package com.run_us.server.global.exceptions;

import com.run_us.server.global.exceptions.enums.CustomResponseCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  private final String logMessage;

  protected BusinessException(CustomResponseCode errorCode) {
    super(errorCode.getClientMessage());
    this.logMessage = errorCode.getLogMessage();
  }

  protected BusinessException(CustomResponseCode errorCode, String logMessage) {
    super(errorCode.getClientMessage());
    this.logMessage = logMessage;
  }

  public static BusinessException of(CustomResponseCode errorCode) {
    return new BusinessException(errorCode, errorCode.getLogMessage());
  }

  public String getName() {
    return this.getClass().getSimpleName();
  }
}

