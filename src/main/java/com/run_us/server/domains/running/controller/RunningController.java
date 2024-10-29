package com.run_us.server.domains.running.controller;

import com.run_us.server.domains.running.controller.model.RunningHttpResponseCode;
import com.run_us.server.domains.running.controller.model.request.RunningCreateRequest;
import com.run_us.server.domains.running.controller.model.response.RunningCreateResponse;
import com.run_us.server.domains.running.service.RunningPreparationService;
import com.run_us.server.domains.running.service.RunningResultService;
import com.run_us.server.domains.running.service.model.JoinedParticipant;
import com.run_us.server.domains.running.service.model.PersonalRecordQueryResult;
import com.run_us.server.global.common.SuccessResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/runnings")
@RequiredArgsConstructor
public class RunningController {

  private final RunningPreparationService runningPreparationService;
  private final RunningResultService runningResultService;

  @PostMapping
  public SuccessResponse<RunningCreateResponse> createRunning(@RequestBody RunningCreateRequest runningCreateDto) {
    return SuccessResponse.of(RunningHttpResponseCode.RUNNING_CREATED,
        runningPreparationService.createRunning(runningCreateDto));
  }

  @GetMapping("/{runningId}/participants")
  public SuccessResponse<List<JoinedParticipant>> joinedParticipants(@PathVariable String runningId) {
    List<JoinedParticipant> joinedParticipants = runningPreparationService.getJoinedParticipants(
        runningId);
    return SuccessResponse.of(RunningHttpResponseCode.PARTICIPANTS_FETCHED, joinedParticipants);
  }

  /***
   * 특정 러닝의 특정 사용자 기록을 조회하는 API
   * @param runningId 러닝 고유번호
   * @param userId 사용자 고유번호
   * */
  @GetMapping("/{runningId}/records/{userId}")
  public SuccessResponse<PersonalRecordQueryResult> getPersonalRecord(@PathVariable String runningId, @PathVariable String userId) {
    return SuccessResponse.of(RunningHttpResponseCode.SINGLE_RECORD_FETCHED,
        runningResultService.getPersonalRecord(runningId, userId));
  }
}
