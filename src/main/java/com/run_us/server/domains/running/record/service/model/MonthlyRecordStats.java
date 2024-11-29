package com.run_us.server.domains.running.record.service.model;

import java.time.LocalDate;

public interface MonthlyRecordStats {
  LocalDate getDate();

  Long getTotalDistance();

  Long getTotalTime();
}
