package com.run_us.server.domains.user.exception;

import com.run_us.server.global.exception.BusinessException;
import com.run_us.server.global.exception.code.CustomResponseCode;

public class UserAuthException extends BusinessException {

    protected UserAuthException(CustomResponseCode errorCode, String logMessage) {
      super(errorCode, logMessage);
    }

    protected UserAuthException(CustomResponseCode errorCode) {
      super(errorCode);
    }

    public static UserAuthException of(CustomResponseCode errorCode) {
      return new UserAuthException(errorCode);
    }
}
