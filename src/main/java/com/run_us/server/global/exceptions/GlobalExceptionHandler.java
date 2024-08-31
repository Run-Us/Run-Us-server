package com.run_us.server.global.exceptions;

import com.run_us.server.global.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /*
  * 예외 처리 핸들러 예시
  *
  * @ExceptionHandler(ExampleException.class)
  * protected ResponseEntity<ErrorResponse> handleExampleException(final ExampleException e) {
  *   log.error("ExampleException Caught! [{}]", e.getLogMessage());
  *   final ErrorResponse response = ErrorResponse.of(ExampleErrorCode.EXAMPLE);
      return new ResponseEntity<>(response, e.getHttpStatusCode());
  * }
  */

  /*
  * BusinessException을 처리하는 핸들러
  * */
  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
    log.error("BusinessException Caught! [{}]", e.getLogMessage());
    final ErrorResponse response = ErrorResponse.of(e.getMessage(), e.getName());
    return new ResponseEntity<>(response, e.getHttpStatusCode());
  }

  /*
  * 예외처리를 하지 않은 모든 예외를 처리하는 핸들러
  * 개발자가 처리하지 않은 예외이므로 500에러를 반환
  * */
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
    log.error("Exception Caught!", e);
    final ErrorResponse response = ErrorResponse.of(e.getMessage(), "UnCaughtException");
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
