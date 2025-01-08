package com.run_us.server.domains.running.run.controller.model;

import com.run_us.server.global.exception.code.CustomResponseCode;
import org.springframework.http.HttpStatus;

public enum RunningHttpResponseCode implements CustomResponseCode {

  // 200
  PARTICIPANTS_FETCHED("RSH2001", "러닝 참여자 조회 성공", "러닝 참여자 조회 성공"),
  ROOM_ID_FETCHED("RSH2002", "대기방 ID 조회 성공", "Passcode Translation 성공"),
  SINGLE_RECORD_FETCHED("RSH2003", "단일 러닝 기록 상세 조회 성공", "단일 러닝 기록 상세 조회 성공"),
  RUN_PREVIEW_FETCHED("RSH2004", "러닝 세션글 조회 성공", "러닝 세션글 조회 성공"),
  RUN_PREVIEW_UPDATED("RSH2005", "러닝 세션글 수정 성공", "러닝 세션글 수정 성공"),
  RUN_PREVIEW_DELETED("RSH2006", "러닝 세션글 삭제 성공", "러닝 세션글 삭제 성공"),
  RUN_RECORD_DELETED("RSH2007", "러닝 기록 삭제 성공", "러닝 기록 삭제 성공"),
  PARTICIPANT_REGISTERED("RSH2008", "러닝 참여자 등록 성공", "러닝 참여자 등록 성공"),
  PARTICIPANT_CANCELED("RSH2009", "러닝 참여자 취소 성공", "러닝 참여자 취소 성공"),

  // 201
  SESSION_RUN_CREATED("RSH2011", "세션런 생성 성공", "러닝 생성 성공"),
  CUSTOM_RUN_CREATED("RSH2012", "커스텀런 생성 성공", "커스텀런 생성 성공"),
  SINGLE_RUN_RECORD_SAVED("RSH2013", "싱글런 기록 저장 성공", "싱글런 기록 저장 성공"),
  GROUP_RUN_RECORD_SAVED("RSH2014", "그룹런 기록 저장 성공", "그룹런 기록 저장 성공"),
  LIVE_ROOM_CREATED("RSH2015", "라이브 러닝방 생성 성공", "라이브 러닝방 생성 성공");

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
