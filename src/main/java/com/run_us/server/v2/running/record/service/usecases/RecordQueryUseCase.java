package com.run_us.server.v2.running.record.service.usecases;

import com.run_us.server.v2.running.record.service.model.RecordPost;
import java.util.List;

public interface RecordQueryUseCase {
  RecordPost getSingleRecordPost(String userId, String runId);
  List<RecordPost> getUserRecordPosts(Integer userId, int page, int fetchSize);
  List<RecordPost> getWeeklyRecordPosts(Integer userId, int page, int fetchSize);
  List<RecordPost> getMonthlyRecordPosts(Integer userId, int page, int fetchSize);
  List<RecordPost> getYearlyRecordPosts(Integer userId, int page, int fetchSize);
}
