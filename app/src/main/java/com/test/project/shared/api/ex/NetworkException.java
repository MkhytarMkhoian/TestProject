package com.test.project.shared.api.ex;

public class NetworkException extends BaseApiException {

  private final String message;
  public NetworkException(String message, Throwable cause) {
    super(message, cause);
    this.message = message;
  }

  public String getFirstErrorMsg() {
    return message;
  }

  @Override public int httpStatusCode() {
    return 0;
  }
}
