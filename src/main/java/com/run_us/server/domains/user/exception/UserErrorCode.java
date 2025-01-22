package com.run_us.server.domains.user.exception;

import com.run_us.server.global.exception.code.CustomResponseCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements CustomResponseCode {

  // 400
  USER_ALREADY_EXISTS("UEH4001", HttpStatus.BAD_REQUEST, "이미 존재하는 사용자", "이미 존재하는 사용자"),
  SIGNUP_FAILED("UEH4002", HttpStatus.BAD_REQUEST, "회원가입 실패", "회원가입 실패"),

  // 401
  LOGIN_FAILED_BY_PROVIDER("UEH4011", HttpStatus.FORBIDDEN, "소셜 로그인 실패", "소셜 로그인 실패"),
  JWT_NOT_FOUND("UEH4012", HttpStatus.UNAUTHORIZED, "JWT 토큰이 존재하지 않습니다.", "JWT 토큰이 존재하지 않습니다."),
  JWT_EXPIRED("UEH4013", HttpStatus.UNAUTHORIZED, "JWT 토큰이 만료되었습니다.", "JWT 토큰이 만료되었습니다."),
  JWT_BROKEN("UEH4014", HttpStatus.UNAUTHORIZED, "JWT 토큰이 손상되었습니다", "JWT 토큰이 손상되었습니다"),
  REFRESH_FAILED("UEH4015", HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다.", "리프레시 토큰이 만료되었습니다."),

  // 404
  USER_NOT_FOUND("UEH4041", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없음", "사용자를 찾을 수 없음"),;

  private final String code;
  private final HttpStatus httpStatusCode;
  private final String clientMessage;
  private final String logMessage;

  UserErrorCode(String code, HttpStatus httpStatusCode, String clientMessage, String logMessage) {
    this.code = code;
    this.httpStatusCode = httpStatusCode;
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
    return this.httpStatusCode;
  }
}
