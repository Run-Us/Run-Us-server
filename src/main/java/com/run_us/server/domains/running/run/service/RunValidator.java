package com.run_us.server.domains.running.run.service;

import com.run_us.server.domains.running.common.RunningErrorCode;
import com.run_us.server.domains.running.common.RunningException;
import com.run_us.server.domains.running.run.domain.SessionAccessLevel;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.service.model.RunCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class RunValidator {

  private final ParticipantService participantService;

  public void validateRunDeletable(Integer userId, Run run) {
    validateIsRunOwner(userId, run);
    if (!run.isDeletable()) {
      throw RunningException.of(RunningErrorCode.RUNNING_SESSION_NOT_MODIFIABLE);
    }
  }

  public void validateIsRunOwner(Integer userId, Run run) {
    if (!run.isHost(userId)) {
      throw RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND);
    }
  }

  public void validateRunJoinable(Run run) {
    if (!run.isJoinable()) {
      throw RunningException.of(RunningErrorCode.RUNNING_NOT_JOINABLE);
    }
  }

  public boolean isCrewRunCreateCommand(RunCreateDto createDto) {
    // ONLY_CREW인데 CREW ID가 없는 경우
    if(createDto.getAccessLevel() == SessionAccessLevel.ONLY_CREW && createDto.getCrewPublicId() != null) {
      throw RunningException.of(RunningErrorCode.RUNNING_SESSION_INVALID);
    }
    // ONLY_CREW + CREW ID or PUBLIC + CREW ID
    return createDto.getAccessLevel() == SessionAccessLevel.ONLY_CREW || createDto.getCrewPublicId() != null;
  }

  public void validateCurrentUserCanStartRun(Integer userId, Run run) {
    if(run.isCreationTimeOver()) {
      throw RunningException.of(RunningErrorCode.LIVE_RUNNING_CREATION_TIME_OVER);
    }
    if(!run.isJoinable()) {
      throw RunningException.of(RunningErrorCode.RUNNING_NOT_JOINABLE);
    }

    if(!participantService.isRegistered(userId, run)) {
      throw RunningException.of(RunningErrorCode.USER_NOT_JOINED);
    }
    if(!run.isLiveSessionCreatableByAnyone() && run.isLiveSessionCreatableByHost()) {
      validateIsRunOwner(userId, run);
    }
  }
}
