package com.run_us.server.global.exception.enums;

import org.springframework.http.HttpStatus;

public enum ExampleErrorCode implements CustomResponseCode {

  EXAMPLE("C001", HttpStatus.INTERNAL_SERVER_ERROR, "예시 에러코드 예시", "예시로 생성한 에러 발생"),
  SUCCESS("C002", HttpStatus.OK, "예시 성공코드 예시", "성공 시에 생성한 로그"),
  BAD_REQUEST("C003",HttpStatus.BAD_REQUEST, "잘못된 요청", "잘못된 요청 발생");

  private final String code;
  private final HttpStatus httpStatusCode;
  private final String clientMessage;
  private final String logMessage;

  ExampleErrorCode(String code, HttpStatus httpStatusCode, String clientMessage, String logMessage) {
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
