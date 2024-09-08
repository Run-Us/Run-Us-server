package com.run_us.server.global.common;

import com.run_us.server.global.exceptions.enums.CustomResponseCode;
import lombok.Getter;

@Getter
public class SuccessResponse extends Response {

  private final Object payload;

  private SuccessResponse(final CustomResponseCode code, final Object payload) {
    super(true, code);
    this.payload = payload;
  }

  public static SuccessResponse messageOnly(final CustomResponseCode code) {
    return new SuccessResponse(code, null);
  }

  public static SuccessResponse of(final CustomResponseCode code, final Object payload) {
    return new SuccessResponse(code, payload);
  }
}
