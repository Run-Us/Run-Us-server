package com.run_us.server.global.exceptions.enums;

public enum ExampleErrorCode implements CustomResponseCode {

  //TODO: 응답 코드
  // KeyCode
  EXAMPLE("C001", "예시 에러코드 예시", "예시로 생성한 에러 발생"),

  SUCCESS("C002", "예시 성공코드 예시", "성공 시에 생성한 로그"),
  // String with Snake_case
  BAD_REQUEST("C003", "잘못된 요청", "잘못된 요청 발생");


  private final String code;
  private final String clientMessage;
  private final String logMessage;

  ExampleErrorCode(String code, String clientMessage, String logMessage) {
    this.code = code;
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
}
