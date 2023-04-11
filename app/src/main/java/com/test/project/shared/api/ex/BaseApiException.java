package com.test.project.shared.api.ex;

public abstract class BaseApiException extends RuntimeException {
  public BaseApiException(String message, Throwable cause) {
    super(message, cause);
  }

  public abstract String getFirstErrorMsg();

  public abstract int httpStatusCode();

  @Override public String getMessage() {
    return getFirstErrorMsg();
  }
}
