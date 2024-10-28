package com.run_us.server.global.exception;

import com.run_us.server.global.exception.code.CustomResponseCode;

public class UserSocketException extends BusinessException {
    protected UserSocketException(CustomResponseCode errorCode) {
        super(errorCode);
    }

    protected UserSocketException(CustomResponseCode errorCode, String logMessage) {
        super(errorCode, logMessage);
    }


    public static UserSocketException of(CustomResponseCode errorcode) {
        return new UserSocketException(errorcode);
    }
}
