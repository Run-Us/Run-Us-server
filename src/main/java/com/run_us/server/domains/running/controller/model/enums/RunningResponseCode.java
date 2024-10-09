package com.run_us.server.domains.running.controller.model.enums;

import com.run_us.server.global.exception.enums.CustomResponseCode;
import org.springframework.http.HttpStatus;

/**
 * websocket 응답 타입 enum
 */
public enum RunningResponseCode implements CustomResponseCode {

  START_RUNNING("RS001", "러닝 시작", "러닝 시작"),
  UPDATE_LOCATION("RS002", "위치 전송", "위치 전송"),
  UPDATE_RECORD("RS003", "기록 전송", "기록 전송"),
  PAUSE_RUNNING("RS004", "러닝 일시정지", "러닝 일시정지"),
  RESUME_RUNNING("RS005", "러닝 재시작", "러닝 재시작"),
  ARRIVE_RUNNING("RS006", "러닝 도착", "러닝 도착"),
  END_RUNNING("RS007", "러닝 종료", "러닝 종료"),
  ;

  private final String code;
  private final String clientMessage;
  private final String logMessage;

  RunningResponseCode(String code, String clientMessage, String logMessage) {
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
