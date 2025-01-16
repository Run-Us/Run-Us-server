package com.run_us.server.domains.running.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RunningConst {
  public static final long UPDATE_INTERVAL = 1000; // 전체 참가자의 위치 업데이트 주기(1초)
  public static final double SIGNIFICANT_DISTANCE = 4; // 즉시 업데이트 중요 이벤트 기준(N미터 이상 이동시)
  public static final int MAX_LIVE_SESSION_CREATION_TIME = 10; // 라이브세션 생성 최대 대기 시간(10분 까지)
  public static final int LIVE_SESSION_ALLOW_ALL_TIME = 5; // 방장 외 라이브세션 생성 가능 시간 (5분 이후)

  public static final String RUNNING_PREFIX = "running:";
  public static final String STATUS_SUFFIX = ":status";
  public static final String LOCATION_SUFFIX = ":location";

  public static final String RUNNING_WS_SEND_PREFIX = "/topic/runnings/";
  public static final String RUNNING_WS_SUBSCRIBE_PATH = "runnings";
}
