package com.run_us.server.domains.user.controller.model.response;

import com.run_us.server.global.exception.code.CustomResponseCode;
import org.springframework.http.HttpStatus;

public enum UserHttpResponseCode implements CustomResponseCode {
  MY_PAGE_DATA_FETCHED("USH2001", "마이페이지 데이터 조회 성공", "마이페이지 데이터 조회 성공"),
  SIGNUP_SUCCESS("USH2002", "회원가입 성공", "회원가입 성공"),
  LOGIN_SUCCESS("USH2003", "로그인 성공", "로그인 성공"),
  REFRESH_SUCCESS("USH2004", "토큰 재발급 성공", "토큰 재발급 성공"),
  ;

  private final String code;
  private final String clientMessage;
  private final String logMessage;

  UserHttpResponseCode(String code, String clientMessage, String logMessage) {
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
