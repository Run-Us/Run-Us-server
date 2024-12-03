package com.run_us.server.domains.running.common;

import java.util.Optional;

public interface PassCodeRegistry {
  String generateAndGetPassCode(String runId);
  void unregisterPassCode(String passCode);
  Optional<String> getRunIdByPassCode(String passCode);
}
