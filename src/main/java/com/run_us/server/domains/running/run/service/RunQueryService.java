package com.run_us.server.domains.running.run.service;

import com.run_us.server.domains.running.common.RunningErrorCode;
import com.run_us.server.domains.running.common.RunningException;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.domain.RunStatus;
import com.run_us.server.domains.running.run.domain.RunType;
import com.run_us.server.domains.running.run.domain.SessionAccessLevel;
import com.run_us.server.domains.running.run.repository.RunRepository;
import com.run_us.server.domains.running.run.service.model.GetCrewCardRun;
import com.run_us.server.domains.running.run.service.model.JoinedRunPreviewResponse;
import com.run_us.server.domains.running.run.service.model.GetRunPreviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RunQueryService {

  private final RunRepository runRepository;

  public Run findByRunId(Integer runId) {
    return runRepository
        .findById(runId)
        .orElseThrow(() -> RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND));
  }

  public Run findByRunPublicId(String runPublicId) {
    return runRepository
        .findByPublicId(runPublicId)
        .orElseThrow(() -> RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND));
  }

  public Slice<Run> findAllByCrewId(Integer crewId, Integer page, Integer size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    return runRepository.findAllByCrewId(crewId, pageRequest);
  }

  public Slice<Run> findAllByCrewIdAndAccessLevel(Integer crewId, SessionAccessLevel accessLevel, Integer page, Integer size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    return runRepository.findAllByCrewIdAndAccessLevel(crewId, accessLevel, pageRequest);
  }

  public GetRunPreviewResponse getRunPreviewById(Integer runId) {
    Run run = findByRunId(runId);
    GetRunPreviewResponse response = runRepository.findByRunId(runId);
    response.setRunPaces(List.copyOf(run.getPaceCategories()));
    return response;
  }

  // 2가지 쿼리 발생 : 1. 세션 정보 2. 페이스 태그 정보
  public List<JoinedRunPreviewResponse> getJoinedRunPreviews(Integer userId, Integer page, Integer size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    Map<String, JoinedRunPreviewResponse> previews =  runRepository.findJoinedRunPreviews(userId, pageRequest)
        .stream()
        .collect(Collectors.toMap(JoinedRunPreviewResponse::getRunningPublicId, preview -> preview));
    List<Run> runs = runRepository.findAllByPublicId(previews.keySet().stream().toList());
    updateJoinedRunPreviewWithPaceTypes(runs, previews);
    return new ArrayList<>(previews.values());
  }

  private void updateJoinedRunPreviewWithPaceTypes(List<Run> runs, Map<String, JoinedRunPreviewResponse> previews) {
    for (Run run : runs) {
      JoinedRunPreviewResponse preview = previews.get(run.getPublicId());
      preview.setRunPaces(run.getPaceCategories());
    }
  }

  public GetCrewCardRun getCrewCardRun(Integer crewId) {
    Optional<Run> regularRunOp = runRepository.findFirstForCrewCard(crewId, RunType.GROUP_REGULAR, RunStatus.WAITING);
    Optional<Run> irregularRunOp = runRepository.findFirstForCrewCard(crewId, RunType.GROUP_IRREGULAR, RunStatus.WAITING);

    return GetCrewCardRun.from(regularRunOp.orElse(null), irregularRunOp.orElse(null));
  }
}
