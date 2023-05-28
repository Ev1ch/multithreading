package common;

import java.util.Arrays;
import java.util.Random;

public class Matrix {
  private static final String ELEMENT_PRINT_FORMAT = "%8.1f";

  private final int rowsNumber;
  private final int columnsNumber;
  private double[][] matrix;

  public Matrix(double[][] matrix) {
    this.matrix = matrix;
    rowsNumber = matrix.length;
    columnsNumber = matrix[0].length;
  }

  public Matrix(int rowsNumber, int columnsNumber) {
    matrix = new double[rowsNumber][columnsNumber];
    this.rowsNumber = rowsNumber;
    this.columnsNumber = columnsNumber;
  }

  public void fill(double number) {
    for (int i = 0; i < rowsNumber; i++) {
      for (int j = 0; j < columnsNumber; j++) {
        matrix[i][j] = number;
      }
    }
  }

  public double get(int rowIndex, int columnIndex) {
    return matrix[rowIndex][columnIndex];
  }

  public void set(int rowIndex, int columnIndex, double value) {
    matrix[rowIndex][columnIndex] = value;
  }

  public void transpose() {
    final var transposedMatrix = new double[columnsNumber][rowsNumber];

    for (int i = 0; i < columnsNumber; i++) {
      for (int j = 0; j < rowsNumber; j++) {
        transposedMatrix[i][j] = matrix[j][i];
      }
    }

    matrix = transposedMatrix;
  }

  public double[] getRow(int index) {
    return matrix[index];
  }

  public double[] getColumn(int index) {
    return Arrays.stream(matrix).mapToDouble(doubles -> doubles[index]).toArray();
  }

  public int getRowsNumber() {
    return rowsNumber;
  }

  public int getColumnsNumber() {
    return columnsNumber;
  }

  public double[][] getArrayed() {
    final var arrayedMatrix = new double[rowsNumber][columnsNumber];

    for (int i = 0; i < rowsNumber; i++) {
      for (int j = 0; j < columnsNumber; j++) {
        arrayedMatrix[i][j] = matrix[i][j];
      }
    }

    return arrayedMatrix;
  }

  public void print() {
    for (int i = 0; i < rowsNumber; i++) {
      for (int j = 0; j < columnsNumber; j++) {
        System.out.printf(ELEMENT_PRINT_FORMAT, matrix[i][j]);
      }

      System.out.println();
    }
  }

  public void print(String format) {
    for (int i = 0; i < rowsNumber; i++) {
      for (int j = 0; j < columnsNumber; j++) {
        System.out.printf(format, matrix[i][j]);
      }

      System.out.println();
    }
  }

  public static Matrix getRandom(
      int rowsNumber,
      int columnsNumber) {
    final var random = new Random();
    final var matrix = new Matrix(rowsNumber, columnsNumber);

    for (int i = 0; i < rowsNumber; i++) {
      for (int j = 0; j < columnsNumber; j++) {
        matrix.set(i, j, random.nextInt());
      }
    }

    return matrix;
  }

  public static Matrix getFilled(
      int rowsNumber,
      int columnsNumber,
      int number) {
    final var matrix = new Matrix(rowsNumber, columnsNumber);

    matrix.fill(number);

    return matrix;
  }

  public static Matrix transpose(Matrix initialMatrix) {
    final int rowsNumber = initialMatrix.getRowsNumber();
    final int columnsNumber = initialMatrix.getColumnsNumber();

    final var matrix = new Matrix(columnsNumber, rowsNumber);

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

    final var rowsNumber = firstMatrix.getRowsNumber();
    final var columnsNumber = firstMatrix.getColumnsNumber();

    final var matrix = new Matrix(rowsNumber, columnsNumber);

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

    final var rowsNumber = firstMatrix.getRowsNumber();
    final var columnsNumber = secondMatrix.getColumnsNumber();

    final var matrix = new Matrix(rowsNumber, columnsNumber);

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

  public static boolean areEqual(Matrix firstMatrix, Matrix secondMatrix) {
    if (firstMatrix.getRowsNumber() != secondMatrix.getRowsNumber() ||
        firstMatrix.getColumnsNumber() != secondMatrix.getColumnsNumber()) {
      return false;
    }

    for (int i = 0; i < firstMatrix.getRowsNumber(); i++) {
      for (int j = 0; j < firstMatrix.getColumnsNumber(); j++) {
        if (firstMatrix.get(i, j) != secondMatrix.get(i, j)) {
          return false;
        }
      }
    }

    return true;
  }
}