package common;

public class Result<T> {
  private T _content;

  public Result(T content) {
    _content = content;
  }

  public T getContent() {
    return _content;
  }
}