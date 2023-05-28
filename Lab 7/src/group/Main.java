package group;

import mpi.MPI;
import common.Matrix;
import common.Process;

public class Main {
  public static int NUMBER_OF_ROWS_IN_A = 1000;
  public static int NUMBER_OF_COLS_IN_A = 1000;
  public static int NUMBER_OF_COLS_IN_B = 1000;

  public static void main(String[] args) {
    var a = Matrix.getRandom(NUMBER_OF_ROWS_IN_A, NUMBER_OF_COLS_IN_A).getArrayed();
    var b = Matrix.getRandom(NUMBER_OF_COLS_IN_A, NUMBER_OF_COLS_IN_B).getArrayed();
    var c = new Matrix(NUMBER_OF_ROWS_IN_A, NUMBER_OF_COLS_IN_B).getArrayed();

    MPI.Init(args);

    var taskId = MPI.COMM_WORLD.Rank();
    var tasksNumber = MPI.COMM_WORLD.Size();
    var startTime = 0l;
    var endTime = 0l;

    if (tasksNumber < 2) {
      System.err.println("Tasks number is too small.");
      MPI.COMM_WORLD.Abort(1);
      return;
    }

    if (taskId == Process.MASTER.ordinal()) {
      System.out.println("MPI tasks number: " + tasksNumber + ".");
      startTime = System.currentTimeMillis();
    }

    var rowsPerTask = NUMBER_OF_ROWS_IN_A / tasksNumber;
    var extraRows = NUMBER_OF_ROWS_IN_A % tasksNumber;

    var rowsNumber = new int[tasksNumber];
    var rowsOffsets = new int[tasksNumber];
    for (int i = 0; i < tasksNumber; i++) {
      rowsNumber[i] = i < extraRows ? rowsPerTask + 1 : rowsPerTask;
      rowsOffsets[i] = i == 0 ? 0 : rowsOffsets[i - 1] + rowsNumber[i - 1];
    }
    var rowsInTask = rowsNumber[taskId];

    var aRowsBuffer = new double[rowsInTask][NUMBER_OF_COLS_IN_A];
    MPI.COMM_WORLD.Scatterv(
        a,
        0,
        rowsNumber,
        rowsOffsets,
        MPI.OBJECT,
        aRowsBuffer,
        0,
        rowsInTask,
        MPI.OBJECT,
        Process.MASTER.ordinal());

    MPI.COMM_WORLD.Bcast(b, 0, NUMBER_OF_COLS_IN_A, MPI.OBJECT, Process.MASTER.ordinal());

    var cRowsBuffer = new double[rowsInTask][NUMBER_OF_COLS_IN_B];
    for (int k = 0; k < NUMBER_OF_COLS_IN_B; k++) {
      for (int i = 0; i < rowsInTask; i++) {
        for (int j = 0; j < NUMBER_OF_COLS_IN_A; j++) {
          cRowsBuffer[i][k] += aRowsBuffer[i][j] * b[j][k];
        }
      }
    }

    MPI.COMM_WORLD.Gatherv(
        cRowsBuffer,
        0,
        rowsInTask,
        MPI.OBJECT,
        c,
        0,
        rowsNumber,
        rowsOffsets,
        MPI.OBJECT,
        Process.MASTER.ordinal());

    // MPI.COMM_WORLD.Allgatherv(cRowsBuffer, 0, rowsInTask, MPI.OBJECT, c, 0,
    // rowsNumber, rowsOffsets, MPI.OBJECT);

    if (taskId == Process.MASTER.ordinal()) {
      endTime = System.currentTimeMillis();

      System.out.println("Execution time: " + (endTime - startTime) + " ms.");
      System.out.println(
          "Is result valid: " + Matrix.areEqual(Matrix.multiply(new Matrix(a), new Matrix(b)), new Matrix(c)) + ".");
    }

    MPI.Finalize();
  }
}