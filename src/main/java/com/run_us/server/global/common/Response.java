package com.run_us.server.global.common;

import com.run_us.server.global.exception.code.CustomResponseCode;
import lombok.Getter;

@Getter
public class Response {

  private final boolean success;
  private final String message;
  private final String code;

  protected Response(final boolean success, final String message, final String code) {
    this.success = success;
    this.message = message;
    this.code = code;
  }

  protected Response(final boolean success, final CustomResponseCode code) {
    this.success = success;
    this.message = code.getClientMessage();
    this.code = code.getCode();
  }
}
