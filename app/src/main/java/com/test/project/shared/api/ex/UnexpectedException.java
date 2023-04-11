package com.test.project.shared.api.ex;

public class UnexpectedException extends BaseApiException {

  private final String message;
  private final int code;

  public UnexpectedException(String message, Throwable cause, int httpCode) {
    super(message, cause);
    this.message = message;
    this.code = httpCode;
  }

  public String getFirstErrorMsg() {
    return message;
  }

  @Override public int httpStatusCode() {
    return code;
  }
}
