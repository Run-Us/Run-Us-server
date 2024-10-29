package com.run_us.server.domains.running.controller.model;

import com.run_us.server.global.exception.code.CustomResponseCode;
import org.springframework.http.HttpStatus;

public enum RunningHttpResponseCode implements CustomResponseCode {
  RUNNING_CREATED("RSH2001", "러닝 생성 성공", "러닝 생성 성공"),
  PARTICIPANTS_FETCHED("RSH2002", "러닝 참여자 조회 성공", "러닝 참여자 조회 성공"),
  SINGLE_RECORD_SAVED("RSH2003", "싱글런 기록 저장 성공", "싱글런 기록 저장 성공"),
  ROOM_ID_FETCHED("RSH2004", "대기방 ID 조회 성공", "Passcode Translation 성공"),
  SINGLE_RECORD_FETCHED("RSH2005", "단일 러닝 기록 상세 조회 성공", "단일 러닝 기록 상세 조회 성공"),
  RUNNING_DELETED("RSH2006", "러닝 삭제 성공", "러닝 삭제 성공");

  private final String code;
  private final String clientMessage;
  private final String logMessage;

  RunningHttpResponseCode(String code, String clientMessage, String logMessage) {
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
