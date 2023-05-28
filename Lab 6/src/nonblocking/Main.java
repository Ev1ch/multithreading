package nonblocking;

import java.util.ArrayList;

import common.Matrix;
import common.MessageTag;
import common.Process;
import mpi.MPI;
import mpi.Request;

public class Main {
  public static int NUMBER_OF_ROWS_IN_A = 2000;
  public static int NUMBER_OF_COLS_IN_A = 2000;
  public static int NUMBER_OF_COLS_IN_B = 2000;

  public static void main(String[] args) {
    int[] rows = {
        0
    };
    int[] offset = {
        0
    };

    var a = Matrix.getRandom(NUMBER_OF_ROWS_IN_A, NUMBER_OF_COLS_IN_A).getArrayed();
    var b = Matrix.getRandom(NUMBER_OF_COLS_IN_A, NUMBER_OF_COLS_IN_B).getArrayed();
    var c = new Matrix(NUMBER_OF_ROWS_IN_A, NUMBER_OF_COLS_IN_B).getArrayed();

    MPI.Init(args);
    var taskId = MPI.COMM_WORLD.Rank();
    var tasksNumber = MPI.COMM_WORLD.Size();
    var workersNumber = tasksNumber - 1;

    if (tasksNumber < 2) {
      System.err.println("Tasks number is too small.");
      MPI.COMM_WORLD.Abort(1);
      return;
    }

    if (taskId == Process.MASTER.ordinal()) {
      System.out.println("MPI tasks number: " + tasksNumber + ".");

      var startTime = System.currentTimeMillis();
      var rowsPerTaskNumber = NUMBER_OF_ROWS_IN_A / workersNumber;
      var extraRowsNumber = NUMBER_OF_ROWS_IN_A % workersNumber;

      for (int destination = 1; destination <= workersNumber; destination++) {
        rows[0] = (destination <= extraRowsNumber) ? rowsPerTaskNumber + 1 : rowsPerTaskNumber;
        var offsetRequest = MPI.COMM_WORLD.Isend(offset, 0, 1, MPI.INT, destination,
            MessageTag.OFFSET_FROM_MASTER.ordinal());
        var rowsRequest = MPI.COMM_WORLD.Isend(rows, 0, 1, MPI.INT, destination, MessageTag.ROWS_FROM_MASTER.ordinal());

        MPI.COMM_WORLD.Isend(a, offset[0], rows[0], MPI.OBJECT, destination, MessageTag.A_FROM_MASTER.ordinal());
        MPI.COMM_WORLD.Isend(b, 0, NUMBER_OF_COLS_IN_A, MPI.OBJECT, destination, MessageTag.B_FROM_MASTER.ordinal());

        offsetRequest.Wait();
        rowsRequest.Wait();

        offset[0] += rows[0];
      }

      var subTasksRequests = new ArrayList<Request>();

      for (int source = 1; source <= workersNumber; source++) {
        var offsetRequest = MPI.COMM_WORLD.Irecv(offset, 0, 1, MPI.INT, source,
            MessageTag.OFFSET_FROM_WORKER.ordinal());
        var rowsRequest = MPI.COMM_WORLD.Irecv(rows, 0, 1, MPI.INT, source, MessageTag.ROWS_FROM_WORKER.ordinal());

        offsetRequest.Wait();
        rowsRequest.Wait();

        subTasksRequests
            .add(MPI.COMM_WORLD.Irecv(c, offset[0], rows[0], MPI.OBJECT, source, MessageTag.C_FROM_WORKER.ordinal()));
      }

      for (var subTaskRequest : subTasksRequests) {
        subTaskRequest.Wait();
      }

      var endTime = System.currentTimeMillis();

      System.out.println("Execution time: " + (endTime - startTime) + " ms.");
      System.out.println(
          "Is result valid: " + Matrix.areEqual(Matrix.multiply(new Matrix(a), new Matrix(b)), new Matrix(c)) + ".");
    } else {
      var offsetRequest = MPI.COMM_WORLD.Irecv(offset, 0, 1, MPI.INT, Process.MASTER.ordinal(),
          MessageTag.OFFSET_FROM_MASTER.ordinal());
      var rowsRequest = MPI.COMM_WORLD.Irecv(rows, 0, 1, MPI.INT, Process.MASTER.ordinal(),
          MessageTag.ROWS_FROM_MASTER.ordinal());
      var bRequest = MPI.COMM_WORLD.Irecv(b, 0, NUMBER_OF_COLS_IN_A, MPI.OBJECT, Process.MASTER.ordinal(),
          MessageTag.B_FROM_MASTER.ordinal());

      offsetRequest.Wait();
      rowsRequest.Wait();

      MPI.COMM_WORLD.Isend(offset, 0, 1, MPI.INT, Process.MASTER.ordinal(), MessageTag.OFFSET_FROM_WORKER.ordinal());
      MPI.COMM_WORLD.Isend(rows, 0, 1, MPI.INT, Process.MASTER.ordinal(), MessageTag.ROWS_FROM_WORKER.ordinal());

      var aRequest = MPI.COMM_WORLD.Irecv(a, 0, rows[0], MPI.OBJECT, Process.MASTER.ordinal(),
          MessageTag.A_FROM_MASTER.ordinal());

      aRequest.Wait();
      bRequest.Wait();

      for (int k = 0; k < NUMBER_OF_COLS_IN_B; k++) {
        for (int i = 0; i < rows[0]; i++) {
          for (int j = 0; j < NUMBER_OF_COLS_IN_A; j++) {
            c[i][k] += a[i][j] * b[j][k];
          }
        }
      }

      MPI.COMM_WORLD.Isend(c, 0, rows[0], MPI.OBJECT, Process.MASTER.ordinal(), MessageTag.C_FROM_WORKER.ordinal());
    }

    MPI.Finalize();
  }
}