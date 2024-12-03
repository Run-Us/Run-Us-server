package com.run_us.server.domains.running.run.service.usecase;

import com.run_us.server.domains.running.run.service.model.FetchRunningIdResponse;
import com.run_us.server.domains.running.run.service.model.ParticipantInfo;
import com.run_us.server.global.common.SuccessResponse;

import java.util.List;

public interface RunRegisterUseCase {
  void registerRun(String userId, String runPublicId);

  void cancelRun(String userId, String runPublicId);

  SuccessResponse<List<ParticipantInfo>> getRunParticipants(String runPublicId);

  SuccessResponse<FetchRunningIdResponse> getRunningIdWithPasscode(String passcode);
}
