package com.run_us.server.domains.running.exception;

import com.run_us.server.global.exceptions.BusinessException;
import com.run_us.server.global.exceptions.enums.CustomResponseCode;

public class RunningException extends BusinessException {

  protected RunningException(
      CustomResponseCode errorCode) {
    super(errorCode);
  }

  protected RunningException(CustomResponseCode errorCode, String logMessage) {
    super(errorCode, logMessage);
  }

  public static RunningException of(CustomResponseCode errorCode) {
    return new RunningException(errorCode);
  }
}
