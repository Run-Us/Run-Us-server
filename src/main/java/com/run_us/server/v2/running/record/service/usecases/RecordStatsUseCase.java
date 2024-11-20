package com.run_us.server.v2.running.record.service.usecases;

public interface RecordStatsUseCase {
  void getUserWeeklyStats(Integer userId);

  void getUserMonthlyStats(Integer userId);

  void getUserYealyStats(Integer userId);
}
