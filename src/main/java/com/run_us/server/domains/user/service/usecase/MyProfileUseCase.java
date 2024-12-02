package com.run_us.server.domains.user.service.usecase;

import com.run_us.server.domains.user.service.model.MyPageResult;

import java.time.ZonedDateTime;
import java.util.List;

public interface MyProfileUseCase {
  MyPageResult getMyPage(String userId);
  List<Long> getMyPageDistanceStatistics(String type, String userId, ZonedDateTime startDate, ZonedDateTime endDate);
}
