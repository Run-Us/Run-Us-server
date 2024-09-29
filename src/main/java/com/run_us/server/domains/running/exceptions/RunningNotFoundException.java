package com.run_us.server.domains.running.exceptions;

import com.run_us.server.global.exceptions.BusinessException;
import com.run_us.server.global.exceptions.enums.CustomResponseCode;

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
