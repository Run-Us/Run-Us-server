package com.run_us.server.v2.running.record.service.model;

import com.run_us.server.v2.running.record.domain.RecordContent;
import com.run_us.server.v2.running.record.domain.RecordStats;
import com.run_us.server.v2.running.record.domain.RunRecord;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// RecordPost는 RunRecord 엔티티를 외부로 노출할때 사용되는 DTO입니다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordPost {
  private RecordStats stats;
  private RecordContent content;

  public RecordPost(RecordStats stats, RecordContent content) {
    this.stats = stats;
    this.content = content;
  }

  public static RecordPost from(RunRecord record) {
    return new RecordPost(record.getRecordStat(), record.getRecordContent());
  }
}
