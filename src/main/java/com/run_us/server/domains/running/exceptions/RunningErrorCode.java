package com.run_us.server.domains.running.exceptions;

import com.run_us.server.global.exceptions.enums.CustomResponseCode;
import org.springframework.http.HttpStatus;

public enum RunningErrorCode implements CustomResponseCode {

  RE001("RE001", "Running not found", "Running not found", HttpStatus.NOT_FOUND);


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
