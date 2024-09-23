package com.run_us.server.domains.running.service;

import com.run_us.server.domains.running.controller.model.request.RunningCreateRequest;
import com.run_us.server.domains.running.controller.model.response.RunningCreateResponse;
import com.run_us.server.domains.running.domain.Running;
import com.run_us.server.domains.running.repository.RunningRepository;
import com.run_us.server.domains.user.model.User;
import com.run_us.server.domains.user.repository.UserRepository;
import com.run_us.server.global.utils.PointGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    log.info("joinRunning : {} +  {}" , runningId, userId);
    Running running = runningRepository.findByPublicKey(runningId);
    User user = userRepository.findByPublicId(userId).orElseThrow(IllegalArgumentException::new);
    running.addParticipant(user);
  }
}
