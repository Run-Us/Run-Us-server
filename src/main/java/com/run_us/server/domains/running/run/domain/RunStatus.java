package com.run_us.server.domains.running.run.domain;

public enum RunStatus {
  WAITING,
  RUNNING,
  CANCELLED,
  FINISHED;

  public static boolean isRunDeletable(final RunStatus status) {
    return status == WAITING || status == CANCELLED;
  }

  public static boolean isJoinable(final RunStatus status) {
    return status == WAITING;
  }
}
