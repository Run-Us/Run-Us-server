package com.run_us.server.v2.running.record.controller;

import com.run_us.server.global.common.SuccessResponse;
import com.run_us.server.v2.RunningHttpResponseCode;
import com.run_us.server.v2.running.record.controller.model.GroupRunRecordRequest;
import com.run_us.server.v2.running.record.controller.model.SingleRunRecordRequest;
import com.run_us.server.v2.running.record.domain.RecordStats;
import com.run_us.server.v2.running.record.service.model.RecordPost;
import com.run_us.server.v2.running.record.service.usecases.RecordCommandUseCase;
import com.run_us.server.v2.running.record.service.usecases.RecordQueryUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/runnigs/records")
@RequiredArgsConstructor
public class RunRecordController {

  private final RecordQueryUseCase recordQueryUseCase;
  private final RecordCommandUseCase recordCommandUseCase;

  /***
   * 특정 러닝의 특정 사용자 기록을 조회하는 API
   * @param runningId 러닝 고유번호
   * @param userId 사용자 고유번호
   * */
  @GetMapping("/{runningId}/records/{userId}")
  public SuccessResponse<RecordPost> getPersonalRecord(
      @PathVariable String runningId, @PathVariable String userId) {
    log.info("action=get_personal_record running_id={} user_id={}", runningId, userId);
    return SuccessResponse.of(
        RunningHttpResponseCode.SINGLE_RECORD_FETCHED,
        recordQueryUseCase.getSingleRecordPost(runningId, userId));
  }

  /***
   * 혼자 달리기 종료 후 기록을 저장하는 API
   * requestParam mode=single
   * e.g) /runnings/aggregates?mode=single
   * @param singleRunRecordRequest 달리기 결과
   * @return SuccessResponse
   */
  @PostMapping(value = "/aggregates", params = "mode=single")
  public SuccessResponse saveSingleRunPersonalRecord(
      @RequestBody SingleRunRecordRequest singleRunRecordRequest,
      @RequestAttribute("publicUserId") String userId) {
    log.info("action=save_single_personal_record user_id={}", userId);
    RecordStats recordStats = singleRunRecordRequest.toRecordStats();
    recordCommandUseCase.saveSingleRunRecordStats(userId, recordStats);
    return SuccessResponse.messageOnly(RunningHttpResponseCode.SINGLE_RUN_RECORD_SAVED);
  }

  /***
   * 다수 달리기 종료 후 기록을 저장하는 API
   * requestParam mode=multi
   * e.g) /runnings/aggregates?mode=multi
   * @param multiRunRecordRequest 달리기 결과
   * @return SuccessResponse
   */
  @PostMapping(value = "/aggregates", params = "mode=multi")
  public SuccessResponse saveMultiRunPersonalRecord(
      @RequestBody GroupRunRecordRequest multiRunRecordRequest,
      @RequestAttribute("publicUserId") String userId) {
    log.info(
        "action=save_multi_personal_record user_id={} running_id={}",
        userId,
        multiRunRecordRequest.getRunningId());
    RecordStats recordStats = multiRunRecordRequest.toRecordStats();
    recordCommandUseCase.saveGroupRunRecordStats(
        userId, multiRunRecordRequest.getRunningId(), recordStats);
    return SuccessResponse.messageOnly(RunningHttpResponseCode.GROUP_RUN_RECORD_SAVED);
  }
}
