package com.run_us.server.v2.running.run.service;

import java.time.ZonedDateTime;

public final class DateUtils {

  public static ZonedDateTime getFirstDayOfThisWeek() {
    return ZonedDateTime.now().minusDays(ZonedDateTime.now().getDayOfWeek().getValue() - 1);
  }

  public static ZonedDateTime getFirstDayOfThisMonth() {
    return ZonedDateTime.now().minusDays(ZonedDateTime.now().getDayOfMonth() - 1);
  }

  public static ZonedDateTime getFirstDayOfThisYear() {
    return ZonedDateTime.now().minusDays(ZonedDateTime.now().getDayOfYear() - 1);
  }
}
