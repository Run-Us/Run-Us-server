package com.run_us.server.domains.running.controller.model;

import com.run_us.server.global.exception.code.CustomResponseCode;
import org.springframework.http.HttpStatus;

/**
 * websocket 응답 타입 enum
 */
public enum UserSocketResponseCode implements CustomResponseCode {

  USER_INFO_NOT_EXIST("UES4001", "사용자 정보 확인 불가", "세션에서 사용자 정보 확인 불가"),
  ;

  private final String code;
  private final String clientMessage;
  private final String logMessage;

  UserSocketResponseCode(String code, String clientMessage, String logMessage) {
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

  @Override
  public HttpStatus getHttpStatusCode() {
    return null;
  }
}
