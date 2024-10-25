package com.run_us.server.global.common;

import com.run_us.server.global.exception.code.CustomResponseCode;
import lombok.Getter;

@Getter
public class SuccessResponse<T> extends Response {

  private final T payload;

  private SuccessResponse(final CustomResponseCode code, final T payload) {
    super(true, code);
    this.payload = payload;
  }

  public static SuccessResponse<Void> messageOnly(final CustomResponseCode code) {
    return new SuccessResponse<>(code, null);
  }

  public static <T> SuccessResponse<T> of(final CustomResponseCode code, final T payload) {
    return new SuccessResponse<>(code, payload);
  }
}
