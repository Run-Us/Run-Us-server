package com.run_us.server.domains.running.exception;

import com.run_us.server.global.exception.code.CustomResponseCode;
import org.springframework.http.HttpStatus;

public enum RunningErrorCode implements CustomResponseCode {

  RUNNING_NOT_FOUND("RE001", "Running not found", "Running not found", HttpStatus.NOT_FOUND),
  AGGREGATE_FAILED("RE4001", "Failed to Save Running Result", "Failed to Save Running Result",
      HttpStatus.BAD_REQUEST),
  PERSONAL_RECORD_NOT_FOUND("REH4001", "Personal Record not found", "Personal Record not found", HttpStatus.BAD_REQUEST);


  private final String code;
  private final String clientMessage;
  private final String logMessage;
  private final HttpStatus httpStatusCode;

  RunningErrorCode(String code, String clientMessage, String logMessage,
      HttpStatus httpStatusCode) {
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
