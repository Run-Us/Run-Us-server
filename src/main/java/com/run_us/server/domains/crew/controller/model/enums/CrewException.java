package com.run_us.server.domains.crew.controller.model.enums;

import com.run_us.server.global.exception.BusinessException;
import com.run_us.server.global.exception.code.CustomResponseCode;

public class CrewException extends BusinessException {

    public CrewException(CustomResponseCode errorCode) {
        super(errorCode);
    }

    public CrewException(CustomResponseCode errorCode, String logMessage) {
        super(errorCode, logMessage);
    }

    public static CrewException of(CustomResponseCode errorCode) {
        return new CrewException(errorCode);
    }
}
