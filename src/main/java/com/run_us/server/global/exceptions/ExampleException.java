package com.run_us.server.global.exceptions;

import com.run_us.server.global.exceptions.enums.CustomResponseCode;

public class ExampleException extends BusinessException {

  protected ExampleException(CustomResponseCode errorCode,
      String logMessage) {
    super(errorCode, logMessage);
  }

  protected ExampleException(CustomResponseCode errorCode) {
    super(errorCode);
  }

  public static ExampleException of(CustomResponseCode errorcode) {
    return new ExampleException(errorcode);
  }
}