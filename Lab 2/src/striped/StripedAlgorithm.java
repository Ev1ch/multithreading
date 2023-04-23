package striped;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import common.IAlgorithm;
import common.Result;
import common.Matrix;

public class StripedAlgorithm implements IAlgorithm {
  public Result<Matrix> run(Matrix firstMatrix, Matrix secondMatrix) throws RuntimeException {
    return multiple(firstMatrix, secondMatrix);
  }

  private Result<Matrix> multiple(Matrix firstMatrix, Matrix secondMatrix) throws RuntimeException {
    if (!Matrix.canBeMultiplied(firstMatrix, secondMatrix)) {
      throw new IllegalArgumentException(
          "The number of columns of the first matrix must match the number of rows of the second matrix.");
    }

    try {
      var result = new Matrix(firstMatrix.getRowsNumber(), secondMatrix.getColumnsNumber());
      var secondTransposedMatrix = Matrix.transpose(secondMatrix);
      var executor = Executors.newFixedThreadPool(firstMatrix.getRowsNumber());
      List<Callable<Object>> tasks = new ArrayList<>(firstMatrix.getRowsNumber());

      for (int i = 0; i < firstMatrix.getRowsNumber(); i++) {
        for (int j = 0; j < secondMatrix.getColumnsNumber(); j++) {
          var thread = new StripedAlgorithmThread(result, firstMatrix.getRow(i), secondTransposedMatrix.getRow(j), i,
              j);
          tasks.add(Executors.callable(thread));
        }
      }

      executor.invokeAll(tasks);
      executor.shutdown();

      return new Result<>(result);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}