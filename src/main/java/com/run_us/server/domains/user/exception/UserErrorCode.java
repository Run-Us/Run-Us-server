package com.run_us.server.domains.user.exception;

import com.run_us.server.global.exception.code.CustomResponseCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements CustomResponseCode {
  PUBLIC_ID_NOT_FOUND(
      "UEH4001", HttpStatus.BAD_REQUEST, "public id 에 해당하는 사용자 없음", "public id 에 해당하는 사용자 없음"),
  USER_NOT_FOUND("UEH4002", HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없음", "사용자를 찾을 수 없음"),
  USER_ALREADY_EXISTS("UEH4003", HttpStatus.BAD_REQUEST, "이미 존재하는 사용자", "이미 존재하는 사용자"),
  SIGNUP_FAILED("UEH4004", HttpStatus.BAD_REQUEST, "회원가입 실패", "회원가입 실패"),
  LOGIN_FAILED_BY_PROVIDER("UEH4031", HttpStatus.FORBIDDEN, "소셜 로그인 실패", "소셜 로그인 실패");

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
