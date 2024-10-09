package com.run_us.server.global.exception;

import com.run_us.server.global.exception.enums.CustomResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

  private final String logMessage;
  private final CustomResponseCode errorCode;
  private final HttpStatus httpStatusCode;

  protected BusinessException(CustomResponseCode errorCode) {
    super(errorCode.getClientMessage());
    this.logMessage = errorCode.getLogMessage();
    this.errorCode = errorCode;
    this.httpStatusCode = errorCode.getHttpStatusCode();
  }

  protected BusinessException(CustomResponseCode errorCode, String logMessage) {
    super(errorCode.getClientMessage());
    this.logMessage = logMessage;
    this.errorCode = errorCode;
    this.httpStatusCode = errorCode.getHttpStatusCode();
  }

  public static BusinessException of(CustomResponseCode errorCode) {
    return new BusinessException(errorCode, errorCode.getLogMessage());
  }

  public String getName() {
    return this.getClass().getSimpleName();
  }
}

