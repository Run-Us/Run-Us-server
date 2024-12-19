package com.run_us.server.domains.crew.controller.model.enums;

import com.run_us.server.global.exception.code.CustomResponseCode;
import org.springframework.http.HttpStatus;

public enum CrewErrorCode implements CustomResponseCode {
    // 404
    CREW_NOT_FOUND("CEH4041", "Crew not found", "Crew not found", HttpStatus.NOT_FOUND),
    JOIN_REQUEST_NOT_FOUND("CEH4042", "Join request not found", "Join request not found", HttpStatus.NOT_FOUND),

    // 400
    INVALID_JOIN_REQUEST("CEH4001", "Invalid join request", "Invalid join request", HttpStatus.BAD_REQUEST),
    ALREADY_CREW_MEMBER("CEH4002", "User is already a crew member", "User is already a crew member", HttpStatus.BAD_REQUEST),
    DUPLICATE_JOIN_REQUEST("CEH4003", "Duplicate join request", "Duplicate join request", HttpStatus.BAD_REQUEST),

    // 403
    NOT_CREW_OWNER("CEH4031", "User is not crew owner", "User is not crew owner", HttpStatus.FORBIDDEN)
    ;

    private final String code;
    private final String clientMessage;
    private final String logMessage;
    private final HttpStatus httpStatusCode;

    CrewErrorCode(
            String code, String clientMessage, String logMessage, HttpStatus httpStatusCode) {
        this.code = code;
        this.clientMessage = clientMessage;
        this.logMessage = logMessage;
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getClientMessage() {
        return this.clientMessage;
    }

    @Override
    public String getLogMessage() {
        return this.logMessage;
    }

    @Override
    public HttpStatus getHttpStatusCode() {
        return this.httpStatusCode;
    }
}
