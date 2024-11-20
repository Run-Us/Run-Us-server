package com.run_us.server.v2.running.run.service.usecase;

import com.run_us.server.v2.running.run.service.model.ParticipantInfo;
import java.util.List;

public interface RunRegisterUseCase {
  void registerRun(String userId, String runPublicId);

  void cancelRun(String userId, String runPublicId);

  List<ParticipantInfo> getRunParticipants(String runPublicId);
}