package fox;

import java.util.ArrayList;
import common.IAlgorithm;
import common.Result;
import common.Matrix;

public class FoxAlgorithm implements IAlgorithm {
  private int _blockSize;

  private int getBlockSize(int rowsNumber) {
    int blockSize = 0;

    for (int i = 1; i <= Math.ceil((double) rowsNumber / 2); i++) {
      if (rowsNumber % i == 0) {
        blockSize = i;
      }
    }

    return blockSize;
  }

  public Result<Matrix> run(Matrix firstMatrix, Matrix secondMatrix)
      throws RuntimeException {
    _blockSize = getBlockSize(firstMatrix.getRowsNumber());

    return multiple(firstMatrix, secondMatrix);
  }

  private Result<Matrix> multiple(Matrix firstMatrix, Matrix secondMatrix) throws RuntimeException {
    if (!Matrix.canBeMultiplied(firstMatrix, secondMatrix)) {
      throw new IllegalArgumentException(
          "The number of columns of the first matrix must match the number of rows of the second matrix.");
    }

    if (firstMatrix.getRowsNumber() % _blockSize != 0 || firstMatrix.getColumnsNumber() % _blockSize != 0) {
      throw new IllegalArgumentException("The matrixes must be evenly divisible into blocks of size " + _blockSize);
    }

    try {
      var blocksNumber = firstMatrix.getRowsNumber() / _blockSize;
      var result = new Matrix(firstMatrix.getRowsNumber(), secondMatrix.getColumnsNumber());

      var firstMatrixBlocks = splitMatrixIntoBlocks(firstMatrix, _blockSize);
      var secondMatrixBlocks = splitMatrixIntoBlocks(secondMatrix, _blockSize);
      var resultBlocks = splitMatrixIntoBlocks(result, _blockSize);

      var threads = new ArrayList<FoxAlgorithmThread>();

      for (int i = 0; i < blocksNumber; i++) {
        for (int j = 0; j < blocksNumber; j++) {
          for (int k = 0; k < blocksNumber; k++) {
            final var rowIndex = i;
            final var columnIndex = j;
            final var mod = (i + k) % blocksNumber;

            var thread = new FoxAlgorithmThread(resultBlocks, firstMatrixBlocks[rowIndex][mod],
                secondMatrixBlocks[mod][columnIndex],
                rowIndex,
                columnIndex);

            threads.add(thread);
            thread.start();
          }
        }
      }

      for (var thread : threads) {
        thread.join();
      }

      convertBlocksToMatrix(resultBlocks, result);

      return new Result<>(result);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private Matrix[][] splitMatrixIntoBlocks(Matrix matrix, int blockSize) {
    var blocksNumber = matrix.getRowsNumber() / blockSize;
    var blocks = new Matrix[blocksNumber][blocksNumber];

    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks[0].length; j++) {
        blocks[i][j] = new Matrix(blockSize, blockSize);
      }
    }

    for (int i = 0; i < blocksNumber; i++) {
      for (int j = 0; j < blocksNumber; j++) {
        for (int k = 0; k < blockSize; k++) {
          for (int l = 0; l < blockSize; l++) {
            blocks[i][j].set(k, l, matrix.get(i * blockSize + k, j * blockSize + l));
          }
        }
      }
    }

    return blocks;
  }

  private void convertBlocksToMatrix(Matrix[][] blocks, Matrix result) {
    var subMatrixSize = blocks[0][0].getRowsNumber();
    var subMatricesNumber = blocks.length;

    for (int i = 0; i < subMatricesNumber; i++) {
      for (int j = 0; j < subMatricesNumber; j++) {
        var subMatrix = blocks[i][j];

        var subMatrixStartRow = i * subMatrixSize;
        var subMatrixStartColumn = j * subMatrixSize;

        for (int k = 0; k < subMatrixSize; k++) {
          for (int l = 0; l < subMatrixSize; l++) {
            result.set(subMatrixStartRow + k, subMatrixStartColumn + l, subMatrix.get(k, l));
          }
        }
      }
    }
  }
}