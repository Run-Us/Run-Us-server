package com.run_us.server.domains.user.controller;

import com.run_us.server.domains.user.service.model.MyPageResult;
import com.run_us.server.domains.user.controller.model.response.UserHttpResponseCode;
import com.run_us.server.domains.user.service.usecase.MyProfileUseCase;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.run_us.server.global.common.GlobalConst.ZONE_ID;

@RestController
@RequestMapping("/users/my")
@RequiredArgsConstructor
public class MyPageController {

  private final MyProfileUseCase myProfileUseCase;

  @GetMapping
  public SuccessResponse<MyPageResult> getMyPageData(
      @RequestAttribute("publicUserId") String publicId) {
    return SuccessResponse.of(
        UserHttpResponseCode.MY_PAGE_DATA_FETCHED, myProfileUseCase.getMyPage(publicId));
  }

  @GetMapping(value = "/stats")
  public SuccessResponse<List<Long>> getDistanceListInTimeRange(
      @RequestAttribute("publicUserId") String userId,
      @RequestParam String type,
      @RequestParam LocalDate startDate,
      @RequestParam LocalDate endDate) {
    return SuccessResponse.of(
        UserHttpResponseCode.MY_PAGE_DATA_FETCHED,
          myProfileUseCase.getMyPageDistanceStatistics(
            type.trim(), userId, startDate.atStartOfDay(ZONE_ID), endDate.atStartOfDay(ZONE_ID)));
  }
}
