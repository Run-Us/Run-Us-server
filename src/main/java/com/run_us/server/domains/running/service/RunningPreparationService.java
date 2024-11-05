package com.run_us.server.domains.running.service;

import com.run_us.server.domains.running.service.model.JoinedParticipant;
import com.run_us.server.domains.running.controller.model.request.RunningCreateRequest;
import com.run_us.server.domains.running.controller.model.response.RunningCreateResponse;
import com.run_us.server.domains.running.domain.running.Running;
import com.run_us.server.domains.running.exception.RunningErrorCode;
import com.run_us.server.domains.running.exception.RunningException;
import com.run_us.server.domains.running.repository.RunningRepository;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.exception.UserErrorCode;
import com.run_us.server.domains.user.exception.UserException;
import com.run_us.server.domains.user.repository.UserRepository;
import com.run_us.server.global.util.PointGenerator;
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
  public RunningCreateResponse createRunning(RunningCreateRequest runningCreateDto, String userId) {
    User user = userRepository.findByPublicId(userId)
        .orElseThrow(() -> UserException.of(UserErrorCode.PUBLIC_ID_NOT_FOUND));
    Running running = Running.builder()
        .hostId(user.getId())
        .description(runningCreateDto.getDescription())
        .constraints(runningCreateDto.getConstraints())
        .startLocation(PointGenerator.generatePoint(runningCreateDto.getStartLocation()))
        .build();
    runningRepository.save(running);
    return RunningCreateResponse.from(running);
  }

  @Transactional
  public void joinRunning(String runningId, User user) {
    log.info("joinRunning : {} + {}", runningId, user.getId());
    Running running = runningRepository.findByPublicKey(runningId)
        .orElseThrow(() -> RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND));
    running.addParticipant(user);
  }

  @Transactional(readOnly = true)
  public List<JoinedParticipant> getJoinedParticipants(String runningId) {
    Running running = runningRepository.findByPublicKey(runningId)
        .orElseThrow(() -> RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND));
    List<Integer> participantIds = running.getAllParticipantsId();
    return userRepository.findSimpleParticipantsByRunningId(participantIds);
  }

}
