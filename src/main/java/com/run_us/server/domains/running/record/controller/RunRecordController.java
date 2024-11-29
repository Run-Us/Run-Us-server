package com.run_us.server.domains.running.record.controller;

import com.run_us.server.domains.running.record.controller.model.GroupRunRecordRequest;
import com.run_us.server.domains.running.record.controller.model.SingleRunRecordRequest;
import com.run_us.server.domains.running.record.domain.RecordStats;
import com.run_us.server.domains.running.record.service.model.RecordPost;
import com.run_us.server.domains.running.record.service.model.SaveRunRecordResponse;
import com.run_us.server.domains.running.record.service.usecases.RecordCommandUseCase;
import com.run_us.server.domains.running.record.service.usecases.RecordQueryUseCase;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<SuccessResponse<RecordPost>> getPersonalRecord(
      @PathVariable String runningId, @PathVariable String userId) {
    log.info("action=get_personal_record running_id={} user_id={}", runningId, userId);
    SuccessResponse<RecordPost> response =
        recordQueryUseCase.getSingleRecordPost(runningId, userId);
    return ResponseEntity.ok().body(response);
  }

  /***
   * 혼자 달리기 종료 후 기록을 저장하는 API
   * requestParam mode=single
   * e.g) /runnings/aggregates?mode=single
   * @param singleRunRecordRequest 달리기 결과
   * @return SuccessResponse
   */
  @PostMapping(value = "/aggregates", params = "mode=single")
  public ResponseEntity<SuccessResponse<SaveRunRecordResponse>> saveSingleRunPersonalRecord(
      @RequestBody SingleRunRecordRequest singleRunRecordRequest,
      @RequestAttribute("publicUserId") String userId) {
    log.info("action=save_single_personal_record user_id={}", userId);
    RecordStats recordStats = singleRunRecordRequest.toRecordStats();
    SuccessResponse<SaveRunRecordResponse> response =
        recordCommandUseCase.saveSingleRunRecordStats(userId, recordStats);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /***
   * 다수 달리기 종료 후 기록을 저장하는 API
   * requestParam mode=multi
   * e.g) /runnings/aggregates?mode=multi
   * @param multiRunRecordRequest 달리기 결과
   * @return SuccessResponse
   */
  @PostMapping(value = "/aggregates", params = "mode=multi")
  public ResponseEntity<SuccessResponse<SaveRunRecordResponse>> saveMultiRunPersonalRecord(
      @RequestBody GroupRunRecordRequest multiRunRecordRequest,
      @RequestAttribute("publicUserId") String userId) {
    log.info(
        "action=save_multi_personal_record user_id={} running_id={}",
        userId,
        multiRunRecordRequest.getRunningId());
    RecordStats recordStats = multiRunRecordRequest.toRecordStats();
    SuccessResponse<SaveRunRecordResponse> response =
        recordCommandUseCase.saveGroupRunRecordStats(
            userId, multiRunRecordRequest.getRunningId(), recordStats);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
