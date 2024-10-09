package com.run_us.server.domains.user.exception;

import com.run_us.server.global.exception.BusinessException;
import com.run_us.server.global.exception.enums.CustomResponseCode;

public class UserException extends BusinessException {

  protected UserException(CustomResponseCode errorCode, String logMessage) {
    super(errorCode, logMessage);
  }

  protected UserException(CustomResponseCode errorCode) {
    super(errorCode);
  }

  public static UserException of(CustomResponseCode errorCode) {
    return new UserException(errorCode);
  }
}
