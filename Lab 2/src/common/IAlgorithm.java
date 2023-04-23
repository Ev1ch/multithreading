package common;

public interface IAlgorithm {
  public Result<Matrix> run(Matrix firstMatrix, Matrix secondMatrix) throws RuntimeException;
}
