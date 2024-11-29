package com.run_us.server.domains.running.run.service;

import com.run_us.server.domains.running.common.RunningErrorCode;
import com.run_us.server.domains.running.common.RunningException;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.repository.RunRepository;
import com.run_us.server.domains.running.run.service.model.GetRunPreviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
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

  public GetRunPreviewResponse getRunPreviewById(Integer runId) {
    return runRepository.findByRunId(runId);
  }
}
