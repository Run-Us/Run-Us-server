package com.run_us.server.domains.running.live.controller.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


public class RunningSocketResponse {


  /** 러닝 시작 dto */
  @Getter
  @ToString
  public static class StartRunning {
    private final String runningPublicId;
    private final long count;

    public StartRunning(String runningId, long count) {
      this.runningPublicId = runningId;
      this.count = count;
    }
  }

  // DTO e다 분리 / inner class

  /** 러닝 위치 전송 request dto */
  @Getter
  @ToString
  public static class LocationUpdate {
    private final String userPublicId;
    private final Float latitude;
    private final Float longitude;
    private final long count;

    @Builder
    public LocationUpdate(
        final String userPublicId, final Float latitude, final Float longitude, final long count) {
      this.userPublicId = userPublicId;
      this.latitude = latitude;
      this.longitude = longitude;
      this.count = count;
    }
  }

  @Getter
  @ToString
  public static class PauseRunning {
    private final String userPublicId;
    private final long count;

    public PauseRunning(final String userPublicId, long count) {
      this.userPublicId = userPublicId;
      this.count = count;
    }
  }

  @Getter
  @ToString
  public static final class ResumeRunning {

    private final String userPublicId;
    private final String runningPublicId;
    private final long count;

    @Builder
    public ResumeRunning(String userPublicId, final String runningId, final long count) {
      this.userPublicId = userPublicId;
      this.runningPublicId = runningId;
      this.count = count;
    }
  }

  @Getter
  public static class StopRunning {

    private final String userPublicId;
    private final String runningPublicId;
    private final int count;

    @Builder
    public StopRunning(String userPublicId, final String runningId, final int count) {
      this.userPublicId = userPublicId;
      this.runningPublicId = runningId;
      this.count = count;
    }
  }
}
