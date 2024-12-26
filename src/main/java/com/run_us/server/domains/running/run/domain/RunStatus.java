package com.run_us.server.domains.running.run.domain;

public enum RunStatus {
  WAITING,
  RUNNING,
  CANCELLED,
  FINISHED;

  public static boolean isRunDeletable(RunStatus status) {
    return status.equals(WAITING);
  }
}
