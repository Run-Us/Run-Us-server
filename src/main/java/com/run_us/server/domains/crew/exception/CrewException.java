package com.run_us.server.domains.crew.exception;

import com.run_us.server.global.exception.BusinessException;
import com.run_us.server.global.exception.code.CustomResponseCode;

public class CrewException extends BusinessException {

  protected CrewException(CustomResponseCode errorCode, String logMessage) {
    super(errorCode, logMessage);
  }

  public CrewException(CustomResponseCode errorCode) {
    super(errorCode);
  }

  public static CrewException of(CustomResponseCode errorCode) {
    return new CrewException(errorCode);
  }
}
