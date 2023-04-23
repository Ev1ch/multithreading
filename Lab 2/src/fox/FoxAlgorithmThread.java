package fox;

import common.Matrix;

public class FoxAlgorithmThread extends Thread {
  private Matrix[][] _result;
  private Matrix _firstMatrix;
  private Matrix _secondMatrix;
  private int _rowIndex;
  private int _columnIndex;

  public FoxAlgorithmThread(Matrix[][] result, Matrix firstMatrix, Matrix secondMatrix, int rowIndex, int columnIndex) {
    _result = result;
    _firstMatrix = firstMatrix;
    _secondMatrix = secondMatrix;
    _rowIndex = rowIndex;
    _columnIndex = columnIndex;
  }

  @Override
  public void run() {
    Matrix subResult = Matrix.multiply(_firstMatrix, _secondMatrix);
    _result[_rowIndex][_columnIndex] = Matrix.add(_result[_rowIndex][_columnIndex], subResult);
  }
}