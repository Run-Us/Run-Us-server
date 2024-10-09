package com.run_us.server.domains.user.exception;

import com.run_us.server.global.exception.enums.CustomResponseCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements CustomResponseCode {

  PUBLIC_ID_NOT_FOUND("UE001", HttpStatus.UNAUTHORIZED, "public id 에 해당하는 사용자 없음", "public id 에 해당하는 사용자 없음"),
  ;

  private final String code;
  private final HttpStatus httpStatusCode;
  private final String clientMessage;
  private final String logMessage;

  UserErrorCode(String code, HttpStatus httpStatusCode, String clientMessage, String logMessage) {
    this.code = code;
    this.httpStatusCode = httpStatusCode;
    this.clientMessage = clientMessage;
    this.logMessage = logMessage;
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
