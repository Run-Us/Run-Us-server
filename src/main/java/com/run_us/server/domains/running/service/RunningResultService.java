package com.run_us.server.domains.running.service;

import com.run_us.server.domains.running.domain.LocationData;
import com.run_us.server.domains.running.domain.PersonalRecord;
import com.run_us.server.domains.running.domain.Running;
import com.run_us.server.domains.running.domain.RunningType;
import com.run_us.server.domains.running.exception.RunningErrorCode;
import com.run_us.server.domains.running.exception.RunningException;
import com.run_us.server.domains.running.repository.PersonalRecordRepository;
import com.run_us.server.domains.running.repository.RunningRepository;
import com.run_us.server.domains.user.domain.User;
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
   * @param user 요청한 사용자
   * @param locationUpdates locationUpdates 위치 정보 리스트
   */
  @Transactional
  public void saveRunningResult(String runningId, User user, List<LocationData> locationUpdates) {
    log.info("saveRunningResult : runningId : {}, user : {}", runningId, user);
    Running running = runningRepository.findByPublicKey(runningId)
        .orElseThrow(() -> RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND)); // TODO : 호출하는 쪽 try-catch 문에 잡혀서 해당 에러 파악이 안됨
    PersonalRecord personalRecord = PersonalRecord.builder()
        .runningId(running.getId())
        .userId(user.getId())
        .runningType(RunningType.SINGLE)
        .recordData(locationUpdates.stream().toString())
        .build();
    personalRecordRepository.save(personalRecord);
  }
}
