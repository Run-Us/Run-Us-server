package com.run_us.server.domains.running.run.service;

import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.domain.RunStatus;
import com.run_us.server.domains.running.run.domain.RunningPreview;
import com.run_us.server.domains.running.run.repository.RunRepository;
import com.run_us.server.domains.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RunCommandService {

  private final RunRepository runRepository;
  private final RunValidator runValidator;
  private final RunQueryService runQueryService;

  public Run saveNewRun(User user, RunningPreview preview) {
    Run run = new Run(user.getId());
    run.modifySessionInfo(preview);
    return runRepository.save(run);
  }

  public void updateRunPreview(Integer userId, Integer runId, RunningPreview preview) {
    Run run = runQueryService.findByRunId(runId);
    runValidator.validateIsRunOwner(userId, run);
    run.modifySessionInfo(preview);
  }

  public void updateRunStatus(Integer runId, RunStatus updatedStatus) {
    Run run = runQueryService.findByRunId(runId);
    run.changeStatus(updatedStatus);
  }

  public void deleteRun(Integer userId, String runPublicId) {
    Run run = runQueryService.findByRunPublicId(runPublicId);
    runValidator.validateRunDeletable(userId, run);
    runRepository.delete(run);
  }
}
