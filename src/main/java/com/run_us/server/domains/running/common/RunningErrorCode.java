package com.run_us.server.domains.running.common;

import com.run_us.server.global.exception.code.CustomResponseCode;
import org.springframework.http.HttpStatus;

public enum RunningErrorCode implements CustomResponseCode {
  // 404
  RUNNING_NOT_FOUND("REH4041", "Running not found", "Running not found", HttpStatus.NOT_FOUND),
  PERSONAL_RECORD_NOT_FOUND(
      "REH4042", "Personal Record not found", "Personal Record not found", HttpStatus.NOT_FOUND),

  // 400
  AGGREGATE_FAILED(
      "REH4001",
      "Failed to Save Running Result",
      "Failed to Save Running Result",
      HttpStatus.BAD_REQUEST),
  USER_NOT_JOINED(
      "REH4002", "User not joined to Run", "User not joined to Run", HttpStatus.BAD_REQUEST),
  RUNNING_ALREADY_FINISHED(
      "REH4003", "Running Already Finished", "Running Already Finished", HttpStatus.BAD_REQUEST),
  RUNNING_CANCELED(
      "REH4004", "Running Already Canceled", "Running Already Canceled", HttpStatus.BAD_REQUEST),
  ;

  private final String code;
  private final String clientMessage;
  private final String logMessage;
  private final HttpStatus httpStatusCode;

  RunningErrorCode(
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
