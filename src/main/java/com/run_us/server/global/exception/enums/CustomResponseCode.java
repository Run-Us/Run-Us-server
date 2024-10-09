package com.run_us.server.global.exception.enums;

import org.springframework.http.HttpStatus;

public interface CustomResponseCode {

  String getCode();

  String getClientMessage();

  String getLogMessage();

  HttpStatus getHttpStatusCode();
}
