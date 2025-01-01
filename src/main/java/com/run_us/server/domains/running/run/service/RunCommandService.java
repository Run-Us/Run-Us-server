package com.run_us.server.domains.running.run.service;

import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.domain.RunStatus;
import com.run_us.server.domains.running.run.domain.RunningPreview;
import com.run_us.server.domains.running.run.repository.RunRepository;
import com.run_us.server.domains.running.run.service.model.RunCreateDto;
import com.run_us.server.domains.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RunCommandService {

  private final RunRepository runRepository;
  private final RunValidator runValidator;
  private final RunQueryService runQueryService;

  public Run saveNewRun(User user, RunCreateDto createDto) {
    Run run = new Run(user.getId());
    RunningPreview preview = RunningPreview.from(createDto);
    run.modifySessionInfo(preview);
    run.modifyPaceInfo(createDto.getRunPaces());
    return runRepository.save(run);
  }

  public Run saveNewCustomRun(User user) {
    Run run = new Run(user.getId());
    return runRepository.save(run);
  }

  // TODO: 중복 코드
  public Run saveNewCrewRun(User user, Integer crewId, RunCreateDto createDto) {
    Run run = new Run(user.getId(), crewId);
    RunningPreview preview = RunningPreview.from(createDto);
    run.modifySessionInfo(preview);
    run.modifyPaceInfo(createDto.getRunPaces());
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
