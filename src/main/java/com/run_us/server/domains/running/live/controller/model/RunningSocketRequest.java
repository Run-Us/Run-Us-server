package com.run_us.server.domains.running.live.controller.model;

import lombok.Getter;
import lombok.ToString;

public class RunningSocketRequest {

  /** 러닝 시작 dto */
  @Getter
  @ToString
  public static class StartRunning {
    private final String runningPublicId;
    private final long count;

    public StartRunning(final String runningPublicId, long count) {
      this.runningPublicId = runningPublicId;
      this.count = count;
    }
  }

  // DTO e다 분리 / inner class

  /** 러닝 위치 전송 request dto */
  @Getter
  @ToString
  public static class LocationUpdate {
    private final String runningPublicId;
    private final Float latitude;
    private final Float longitude;
    private final int count;

    public LocationUpdate(
        final String runningPublicId, final Float latitude, final Float longitude, final int count) {
      this.runningPublicId = runningPublicId;
      this.latitude = latitude;
      this.longitude = longitude;
      this.count = count;
    }
  }

  @Getter
  @ToString
  public static class PauseRunning {
    private final String runningPublicId;
    private final int count;

    public PauseRunning(final String runningKey, final int count) {
      this.runningPublicId = runningKey;
      this.count = count;
    }
  }

  @Getter
  @ToString
  public static final class ResumeRunning {
    private final String runningPublicId;
    private final int count;

    public ResumeRunning(final String runningPublicId, final int count) {
      this.runningPublicId = runningPublicId;
      this.count = count;
    }
  }

  @Getter
  public static class StopRunning {
    private final String runningPublicId;
    private final int count;

    public StopRunning(final String runningPublicId, final int count) {
      this.runningPublicId = runningPublicId;
      this.count = count;
    }
  }
}
