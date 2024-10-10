package com.run_us.server.domains.running.exception;

import com.run_us.server.global.exception.BusinessException;
import com.run_us.server.global.exception.code.CustomResponseCode;

public class RunningNotFoundException extends BusinessException {

  protected RunningNotFoundException(
      CustomResponseCode errorCode) {
    super(errorCode);
  }

  protected RunningNotFoundException(CustomResponseCode errorCode, String logMessage) {
    super(errorCode, logMessage);
  }

  public static RunningNotFoundException of(CustomResponseCode errorCode) {
    return new RunningNotFoundException(errorCode);
  }
}
