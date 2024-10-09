package com.run_us.server.domains.running.service;

import com.run_us.server.domains.running.service.model.JoinedParticipantsDto;
import com.run_us.server.domains.running.controller.model.request.RunningCreateRequest;
import com.run_us.server.domains.running.controller.model.response.RunningCreateResponse;
import com.run_us.server.domains.running.domain.Running;
import com.run_us.server.domains.running.exceptions.RunningErrorCode;
import com.run_us.server.domains.running.exceptions.RunningException;
import com.run_us.server.domains.running.repository.RunningRepository;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.repository.UserRepository;
import com.run_us.server.global.utils.PointGenerator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RunningPreparationService {

  private final RunningRepository runningRepository;
  private final UserRepository userRepository;

  @Transactional
  public RunningCreateResponse createRunning(RunningCreateRequest runningCreateDto) {
    Running running = Running.builder()
        .description(runningCreateDto.getDescription())
        .constraints(runningCreateDto.getConstraints())
        .startLocation(PointGenerator.generatePoint(runningCreateDto.getStartLocation()))
        .build();
    runningRepository.save(running);
    return RunningCreateResponse.from(running);
  }

  @Transactional
  public void joinRunning(String runningId, String userId) {
    log.info("joinRunning : {} +  {}", runningId, userId);
    Running running = runningRepository.findByPublicKey(runningId)
        .orElseThrow(() -> RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND));
    User user = userRepository.findByPublicId(userId).orElseThrow(IllegalArgumentException::new);
    running.addParticipant(user);
  }

  @Transactional(readOnly = true)
  public List<JoinedParticipantsDto> getJoinedParticipants(String runningId) {
    Running running = runningRepository.findByPublicKey(runningId)
        .orElseThrow(() -> RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND));
    List<Long> participantIds = running.getAllParticipantsId();
    return userRepository.findSimpleParticipantsByRunningId(participantIds);
  }

}
