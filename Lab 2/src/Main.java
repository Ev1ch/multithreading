import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import common.Matrix;
import common.IAlgorithm;
import fox.FoxAlgorithm;
import striped.StripedAlgorithm;

public class Main {
  private static final int[] MATRIX_SIZES = { 4, 6, 8, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 };
  private static final IAlgorithm[] ALGORITHMS = { new StripedAlgorithm(), new FoxAlgorithm() };

  public static void main(String[] args) {
    try {
      for (var matrixSize : MATRIX_SIZES) {
        System.out.println("Matrix size " + matrixSize + ":");

        for (var algorithm : ALGORITHMS) {
          var first = Matrix.getRandom(matrixSize, matrixSize);
          var second = Matrix.getRandom(matrixSize, matrixSize);

          var startTime = System.currentTimeMillis();
          algorithm.run(first, second);
          var endTime = System.currentTimeMillis();

          System.out.println("  " + algorithm.getClass().getSimpleName() + ": " + (endTime - startTime) + " ms");
        }

        System.out.println();
      }
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
  }
}