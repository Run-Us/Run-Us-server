package com.run_us.server.domains.user.service.model;

import com.run_us.server.domains.running.record.service.model.DailyRecordStats;
import com.run_us.server.domains.running.record.service.model.MonthlyRecordStats;
import com.run_us.server.domains.running.record.service.model.RecordStatsAggregation;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.run_us.server.global.common.GlobalConst.TIME_ZONE_ID;
import static com.run_us.server.global.common.GlobalConst.ZONE_ID;

public final class DistanceListMapper {

  public static  List<Long> getWeeklyRunDistanceInTimeRange(
      List<DailyRecordStats> dailyStats, ZonedDateTime start, ZonedDateTime end) {
    List<Long> weeklyDistances = new ArrayList<>(Collections.nCopies(7, 0L));
    fillWeeklyDistances(dailyStats, weeklyDistances);
    return weeklyDistances;
  }

  public static List<Long> getMonthlyRunDistanceInTimeRange(
      List<DailyRecordStats> dailyStats, ZonedDateTime start, ZonedDateTime end) {
    List<Long> monthlyDistances =
        IntStream.range(0, start.toLocalDate().lengthOfMonth())
            .mapToObj(i -> 0L)
            .collect(Collectors.toList());
    fillMonthlyDistances(dailyStats, monthlyDistances);
    return monthlyDistances;
  }

  public static List<Long> getYearlyRunDistanceInTimeRange(
      List<MonthlyRecordStats> monthlyRecordStats, ZonedDateTime start, ZonedDateTime end) {
    List<Long> yearlyDistances =
        IntStream.range(0, 12)
            .mapToObj(i -> 0L)
            .collect(Collectors.toList());

    fillYearlyDistances(monthlyRecordStats, yearlyDistances);

    return yearlyDistances;
  }

  private static void fillWeeklyDistances(List<DailyRecordStats> dailyStats, List<Long> weeklyDistances) {
    for (DailyRecordStats stat : dailyStats) {
      int dayOfWeek = stat.getDate().getDayOfWeek().getValue() - 1;
      weeklyDistances.set(dayOfWeek, stat.getTotalDistance());
    }
  }

  private static void fillMonthlyDistances(
      List<DailyRecordStats> dailyStats, List<Long> monthlyDistances) {
    for (DailyRecordStats stat : dailyStats) {
      int dayOfMonth = stat.getDate().getDayOfMonth() - 1;
      monthlyDistances.set(dayOfMonth, stat.getTotalDistance());
    }
  }

  private static void fillYearlyDistances(
      List<MonthlyRecordStats> monthlyStats, List<Long> yearlyDistances) {
    for (MonthlyRecordStats stat : monthlyStats) {
      int monthOfYear = 0;
//      System.out.println("stat.getDateTime() = " + stat.getDateTime());
      yearlyDistances.set(monthOfYear, stat.getTotalDistance());
    }
  }
}
