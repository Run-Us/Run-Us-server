package com.run_us.server.v2.running.run.service.usecase;

import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.v2.running.run.domain.RunningPreview;
import com.run_us.server.v2.running.run.service.RunCommandService;
import com.run_us.server.v2.running.run.service.RunQueryService;
import com.run_us.server.v2.running.run.service.model.GetRunPreviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RunPreviewUseCaseImpl implements RunPreviewUseCase {

  private final RunCommandService runCommandService;
  private final UserService userService;
  private final RunQueryService runQueryService;

  @Override
  public void updateRunPreview(String userId, Integer runId, RunningPreview preview) {
    User user = userService.getUserByPublicId(userId);
    runCommandService.updateRunPreview(user.getId(), runId, preview);
  }

  @Override
  public GetRunPreviewResponse getRunPreview(Integer runId) {
    return runQueryService.getRunPreviewById(runId);
  }
}
