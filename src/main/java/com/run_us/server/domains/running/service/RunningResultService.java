package com.run_us.server.domains.running.service;

import com.run_us.server.domains.running.domain.LocationData;
import com.run_us.server.domains.running.domain.PersonalRecord;
import com.run_us.server.domains.running.domain.Running;
import com.run_us.server.domains.running.domain.RunningType;
import com.run_us.server.domains.running.exceptions.RunningErrorCode;
import com.run_us.server.domains.running.exceptions.RunningException;
import com.run_us.server.domains.running.repository.PersonalRecordRepository;
import com.run_us.server.domains.running.repository.RunningRepository;
import com.run_us.server.domains.user.model.User;
import com.run_us.server.domains.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RunningResultService {

  private final PersonalRecordRepository personalRecordRepository;
  private final RunningRepository runningRepository;
  private final UserRepository userRepository;

  /***
   * 러닝 결과 저장
   * @param runningId 러닝 키
   * @param userId 유저 아이디
   * @param locationUpdates locationUpdates 위치 정보 리스트
   */
  @Transactional
  public void saveRunningResult(String runningId, String userId, List<LocationData> locationUpdates) {
    Running running = runningRepository.findByPublicKey(runningId)
        .orElseThrow(() -> RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND));
    User user = userRepository.findByPublicId(userId).orElseThrow(IllegalArgumentException::new);
    PersonalRecord personalRecord = PersonalRecord.builder()
        .runningId(running.getId())
        .userId(user.getId())
        .runningType(RunningType.SINGLE)
        .recordData(locationUpdates.stream().toString())
        .build();
    personalRecordRepository.save(personalRecord);
  }
}
