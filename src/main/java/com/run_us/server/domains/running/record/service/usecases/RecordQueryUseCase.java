package com.run_us.server.domains.running.record.service.usecases;

import com.run_us.server.domains.running.record.service.model.RecordPost;
import com.run_us.server.global.common.SuccessResponse;
import java.util.List;

public interface RecordQueryUseCase {
  SuccessResponse<RecordPost> getSingleRecordPost(Long recordPostId);

  SuccessResponse<List<RecordPost>> getUserRecordPosts(Integer userId, int page, int fetchSize);

  SuccessResponse<List<RecordPost>> getWeeklyRecordPosts(Integer userId, int page, int fetchSize);

  SuccessResponse<List<RecordPost>> getMonthlyRecordPosts(Integer userId, int page, int fetchSize);

  SuccessResponse<List<RecordPost>> getYearlyRecordPosts(Integer userId, int page, int fetchSize);
}
