package striped;

import common.Matrix;
import common.Result;

public class StripedAlgorithmThread extends Thread {
  private Matrix _matrix;
  private double[] _row;
  private double[] _column;
  private int _rowIndex;
  private int _columnIndex;

  public StripedAlgorithmThread(Matrix matrix, double[] row, double[] column, int rowIndex, int columnIndex) {
    _matrix = matrix;
    _row = row;
    _column = column;
    _rowIndex = rowIndex;
    _columnIndex = columnIndex;
  }

  public Result<Matrix> getResult() {
    return new Result<>(_matrix);
  }

  @Override
  public void run() {
    for (int i = 0; i < _row.length; i++) {
      var element = _matrix.get(_rowIndex, _columnIndex);
      _matrix.set(_rowIndex, _columnIndex, element + _row[i] * _column[i]);
    }
  }
}