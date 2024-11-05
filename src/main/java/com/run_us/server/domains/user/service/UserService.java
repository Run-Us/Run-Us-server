package com.run_us.server.domains.user.service;

import com.run_us.server.domains.running.service.RunningResultService;
import com.run_us.server.domains.user.controller.model.MyPageResult;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.exception.UserException;
import com.run_us.server.domains.user.repository.UserRepository;
import com.run_us.server.domains.user.service.model.MyRunningRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.run_us.server.domains.user.exception.UserErrorCode.PUBLIC_ID_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RunningResultService runningResultService;
    private static final int PROFILE_FETCHING_RECORD_NUM = 3;

    /**
     * publicId 로 유저 정보를 가져오는 메서드
     * - 이후 여러 도메인에서 쓰임이 많아지면 helper 등으로 분리 예정
     * @param publicId 유저 고유번호(public)
     * @return 유저 정보
     */
    public User getUserByPublicId(String publicId) {
        return userRepository.findByPublicId(publicId).orElseThrow(() -> UserException.of(PUBLIC_ID_NOT_FOUND));
    }

    public MyPageResult getMyPageData(String publicId) {
        User user = getUserByPublicId(publicId);
        List<MyRunningRecord> runningRecords = runningResultService.getKLatestRecordsByUserId(PROFILE_FETCHING_RECORD_NUM, user.getId());
        return MyPageResult.of(user.getProfile(), runningRecords);
    }
}
