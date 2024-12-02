package com.run_us.server.domains.running.record.service.model;

import com.run_us.server.domains.running.record.domain.RecordContent;
import com.run_us.server.domains.running.record.domain.RecordStats;
import com.run_us.server.domains.running.record.domain.RunRecord;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// RecordPost는 RunRecord 엔티티를 외부로 노출할때 사용되는 DTO입니다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordPost {
  private RecordStats stats;
  private RecordContent content;
  private ZonedDateTime createdAt;

  public RecordPost(RecordStats stats, RecordContent content, ZonedDateTime createdAt) {
    this.stats = stats;
    this.content = content;
    this.createdAt = createdAt;
  }

  public static RecordPost fromRunRecords(RunRecord record) {
    return new RecordPost(record.getRecordStat(), record.getRecordContent(), record.getCreatedAt());
  }

  public static List<RecordPost> fromRunRecords(List<RunRecord> records) {
    return records.stream().map(RecordPost::fromRunRecords).toList();
  }
}
