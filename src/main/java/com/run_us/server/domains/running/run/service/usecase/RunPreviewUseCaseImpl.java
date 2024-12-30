package com.run_us.server.domains.running.run.service.usecase;

import com.run_us.server.domains.running.run.controller.model.RunningHttpResponseCode;
import com.run_us.server.domains.running.run.domain.RunningPreview;
import com.run_us.server.domains.running.run.service.RunCommandService;
import com.run_us.server.domains.running.run.service.RunQueryService;
import com.run_us.server.domains.running.run.service.model.GetRunPreviewResponse;
import com.run_us.server.domains.running.run.service.model.UpdatePreviewResponse;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RunPreviewUseCaseImpl implements RunPreviewUseCase {

  private final RunCommandService runCommandService;
  private final UserService userService;
  private final RunQueryService runQueryService;

  @Override
  @Transactional
  public SuccessResponse<UpdatePreviewResponse> updateRunPreview(String userId, Integer runId, RunningPreview preview) {
    User user = userService.getUserByPublicId(userId);
    runCommandService.updateRunPreview(user.getId(), runId, preview);
    return SuccessResponse.of(RunningHttpResponseCode.RUN_PREVIEW_UPDATED,
        new UpdatePreviewResponse(runId, preview));
  }

  @Override
  @Transactional(readOnly = true)
  public SuccessResponse<GetRunPreviewResponse> getRunPreview(Integer runId) {
    return SuccessResponse.of(RunningHttpResponseCode.RUN_PREVIEW_FETCHED,
        runQueryService.getRunPreviewById(runId));
  }
}
