package common;

import java.util.Arrays;
import java.util.Random;

public class Matrix {
  private final String ELEMENT_PRINT_FORMAT = "%8.1f";

  private final int _rowsNumber;
  private final int _columnsNumber;
  private double[][] _matrix;

  public Matrix() {
    _matrix = new double[2][2];
    _rowsNumber = 2;
    _columnsNumber = 2;
  }

  public Matrix(int rowsNumber, int columnsNumber) {
    _matrix = new double[rowsNumber][columnsNumber];
    _rowsNumber = rowsNumber;
    _columnsNumber = columnsNumber;
  }

  public double get(int rowIndex, int columnIndex) {
    return _matrix[rowIndex][columnIndex];
  }

  public void set(int rowIndex, int columnIndex, double value) {
    _matrix[rowIndex][columnIndex] = value;
  }

  public void transpose() {
    double[][] transposedMatrix = new double[_columnsNumber][_rowsNumber];

    for (int i = 0; i < _columnsNumber; i++) {
      for (int j = 0; j < _rowsNumber; j++) {
        transposedMatrix[i][j] = _matrix[j][i];
      }
    }

    _matrix = transposedMatrix;
  }

  public double[] getRow(int index) {
    return _matrix[index];
  }

  public double[] getColumn(int index) {
    return Arrays.stream(matrix).mapdoubleoDouble(doubles -> doubles[index]).toArray();
  }

  public int getRowsNumber() {
    return _rowsNumber;
  }

  public int getColumnsNumber() {
    return _columnsNumber;
  }

  public void print() {
    for (int i = 0; i < _rowsNumber; i++) {
      for (int j = 0; j < _columnsNumber; j++) {
        System.out.printf(ELEMENT_PRINT_FORMAT, _matrix[i][j]);
      }

      System.out.println();
    }
  }

  public void print(String format) {
    for (int i = 0; i < _rowsNumber; i++) {
      for (int j = 0; j < _columnsNumber; j++) {
        System.out.printf(format, _matrix[i][j]);
      }

      System.out.println();
    }
  }

  public static Matrix getRandom(
      int rowsNumber,
      int columnsNumber) {
    final var random = new Random();
    Matrix matrix = new Matrix(rowsNumber, columnsNumber);

    for (int i = 0; i < rowsNumber; i++) {
      for (int j = 0; j < columnsNumber; j++) {
        matrix.set(i, j, random.nextInt());
      }
    }

    return matrix;
  }

  public static Matrix transpose(Matrix initialMatrix) {
    int rowsNumber = initialMatrix.getRowsNumber();
    int columnsNumber = initialMatrix.getColumnsNumber();

    Matrix matrix = new Matrix(columnsNumber, rowsNumber);

    for (int i = 0; i < rowsNumber; i++) {
      for (int j = 0; j < columnsNumber; j++) {
        matrix.set(j, i, initialMatrix.get(i, j));
      }
    }

    return matrix;
  }

  public static Matrix add(Matrix firstMatrix, Matrix secondMatrix) {
    if (firstMatrix.getRowsNumber() != secondMatrix.getRowsNumber() ||
        firstMatrix.getColumnsNumber() != secondMatrix.getColumnsNumber()) {
      throw new IllegalArgumentException("Matrices must have the same size");
    }

    int rowsNumber = firstMatrix.getRowsNumber();
    int columnsNumber = firstMatrix.getColumnsNumber();

    var matrix = new Matrix(rowsNumber, columnsNumber);

    for (int i = 0; i < rowsNumber; i++) {
      for (int j = 0; j < columnsNumber; j++) {
        matrix.set(i, j, firstMatrix.get(i, j) + secondMatrix.get(i, j));
      }
    }

    return matrix;
  }

  public static Matrix multiply(Matrix firstMatrix, Matrix secondMatrix) {
    if (firstMatrix.getColumnsNumber() != secondMatrix.getRowsNumber()) {
      throw new IllegalArgumentException("Matrices must have the same size");
    }

    int rowsNumber = firstMatrix.getRowsNumber();
    int columnsNumber = secondMatrix.getColumnsNumber();

    var matrix = new Matrix(rowsNumber, columnsNumber);

    for (int i = 0; i < rowsNumber; i++) {
      for (int j = 0; j < columnsNumber; j++) {
        for (int k = 0; k < firstMatrix.getColumnsNumber(); k++) {
          var element = matrix.get(i, j);
          matrix.set(i, j, element + firstMatrix.get(i, k) * secondMatrix.get(k, j));
        }
      }
    }

    return matrix;
  }

  public static boolean canBeMultiplied(Matrix firstMatrix, Matrix secondMatrix) {
    return firstMatrix.getColumnsNumber() == secondMatrix.getRowsNumber();
  }
}