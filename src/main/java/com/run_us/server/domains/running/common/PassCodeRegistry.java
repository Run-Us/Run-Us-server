package com.run_us.server.domains.running.common;

public interface PassCodeRegistry {
  String generateAndGetPassCode(String runId);
  void unregisterPassCode(String passCode);
  String getRunIdByPassCode(String passCode);
}
