package com.run_us.server.domains.running.service;

import com.run_us.server.domains.running.domain.record.PersonalRecord;
import com.run_us.server.domains.running.domain.running.Running;
import com.run_us.server.domains.running.exception.RunningErrorCode;
import com.run_us.server.domains.running.exception.RunningException;
import com.run_us.server.domains.running.repository.PersonalRecordRepository;
import com.run_us.server.domains.running.repository.RunningRepository;
import com.run_us.server.domains.running.service.model.PersonalRecordQueryResult;
import com.run_us.server.domains.running.service.model.RunningAggregations;
import com.run_us.server.domains.running.service.model.RunningMapper;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.exception.UserErrorCode;
import com.run_us.server.domains.user.exception.UserException;
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
   * 존재하는 달리기(같이달리기)에 대한 개인 기록 저장
   * @param runningId 러닝 고유번호(public)
   * @param userId 요청 유저 고유번호(public)
   * @param aggregation 러닝 데이터 결과
   */
  @Transactional
  public void saveRecordForExistingRunning(String runningId, String userId, RunningAggregations aggregation) {
    Running running = runningRepository.findByPublicKey(runningId)
        .orElseThrow(() -> RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND));
    User user = userRepository.findByPublicId(userId)
        .orElseThrow(IllegalArgumentException::new);
    user.updateUserRunningInfo(aggregation.getRunningDistanceInMeters(), aggregation.getRunningDurationInMilliSeconds());
    PersonalRecord personalRecord = RunningMapper.toPersonalRecord(running.getId(), user.getId(), aggregation);
    personalRecordRepository.save(personalRecord);
  }

  /***
   * 혼자 달리기 기록 저장 시 Running 엔티티와 Record 엔티티 생성
   * @param userId
   * @param aggregation
   * @return
   */
  @Transactional
  public String createSingleRunAndSaveRecord(String userId, RunningAggregations aggregation) {
    User user = userRepository.findByPublicId(userId)
        .orElseThrow(() -> UserException.of(UserErrorCode.PUBLIC_ID_NOT_FOUND));
    Running running = new Running(user.getId());
    running.addParticipant(user);
    runningRepository.save(running);
    PersonalRecord personalRecord = RunningMapper.toPersonalRecord(running.getId(), user.getId(), aggregation);
    personalRecordRepository.save(personalRecord);
    user.updateUserRunningInfo(aggregation.getRunningDistanceInMeters(), aggregation.getRunningDurationInMilliSeconds());
    return running.getPublicKey();
  }

    /***
     * 러닝 개인 기록
     * @param runningId 러닝 고유번호
     * @return 러닝 결과
     */
    public PersonalRecordQueryResult getPersonalRecord(String runningId, String userId) {
      Running running = runningRepository.findByPublicKey(runningId)
              .orElseThrow(() -> RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND));

      User user = userRepository.findByPublicId(userId)
              .orElseThrow(() -> UserException.of(UserErrorCode.PUBLIC_ID_NOT_FOUND));

      return personalRecordRepository.findByUserIdAndRunningId(user.getId(), running.getId())
                .orElseThrow(() -> RunningException.of(RunningErrorCode.PERSONAL_RECORD_NOT_FOUND));
    }

    /***
     * 유저의 모든 개인 기록 조회
     * @param userId 유저 고유번호
     * @return 개인 기록 리스트
     */
    public List<PersonalRecordQueryResult> getAllPersonalRecords(Integer userId) {
      return personalRecordRepository.findAllByUserId(userId);
    }
}
