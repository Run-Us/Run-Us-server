package com.run_us.server.domains.user.domain;

public enum TokenStatus {
  /** 토큰에 문제가 없는지 검증을 시도한 적 있으나, 서비스 토큰 발급까지 진행되지 않은 상태 */
  PENDING,
  /** 토큰에 문제가 없는지 검증이 완료되고, 서비스 토큰 발급까지 완료된 상태 */
  USED
}
