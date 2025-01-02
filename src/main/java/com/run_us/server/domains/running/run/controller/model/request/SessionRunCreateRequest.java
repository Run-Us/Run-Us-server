package com.run_us.server.domains.running.run.controller.model.request;

import com.run_us.server.domains.running.common.RunningErrorCode;
import com.run_us.server.domains.running.common.RunningException;
import com.run_us.server.domains.running.run.domain.RunPace;
import com.run_us.server.domains.running.run.service.model.RunCreateDto;
import com.run_us.server.global.validator.annotation.EnumValid;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Getter
public class SessionRunCreateRequest {
  private final String title;
  private final String description;
  private final ZonedDateTime startDateTime;
  private final String meetingPlace;
  @EnumValid(enumClass = SessionAccessLevel.class)
  private final SessionAccessLevel accessLevel;
  private final String crewPublicId;
  @EnumValid(enumClass = RunPace.class)
  private final Set<RunPace> paceCategories;

  @Builder
  public SessionRunCreateRequest(
      String title,
      String description,
      ZonedDateTime startDateTime,
      String meetingPlace,
      SessionAccessLevel accessLevel,
      String crewPublicId,
      Set<RunPace> paceCategories) {
    validateCrewPublicId(accessLevel, crewPublicId);
    validatePace(paceCategories);
    this.title = title;
    this.description = description;
    this.startDateTime = startDateTime;
    this.meetingPlace = meetingPlace;
    this.accessLevel = accessLevel;
    this.crewPublicId = crewPublicId;
    this.paceCategories = paceCategories;
  }

  public RunCreateDto toRunCreateDto() {
    return new RunCreateDto(title, description, meetingPlace, accessLevel, startDateTime, List.copyOf(paceCategories));
  }

  // 크루 공개일 경우 크루 아이디가 필수
  private void validateCrewPublicId(SessionAccessLevel accessLevel, String crewPublicId) {
    if(accessLevel == SessionAccessLevel.ONLY_CREW && crewPublicId == null) {
      throw RunningException.of(RunningErrorCode.RUNNING_SESSION_INVALID);
    }
  }

  private void validatePace(Set<RunPace> pace) {
    if(pace != null && pace.size() > 3) {
      throw RunningException.of(RunningErrorCode.RUNNING_SESSION_INVALID);
    }
  }
}
