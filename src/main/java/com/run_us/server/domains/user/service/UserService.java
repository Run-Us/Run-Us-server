package com.run_us.server.domains.user.service;

import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.exception.UserException;
import com.run_us.server.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.run_us.server.domains.user.exception.UserErrorCode.PUBLIC_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * publicId 로 유저 정보를 가져오는 메서드
     * - 이후 여러 도메인에서 쓰임이 많아지면 helper 등으로 분리 예정
     * @param publicId
     * @return
     */
    public User getUserByPublicId(String publicId) {
        return userRepository.findByPublicId(publicId).orElseThrow(() -> UserException.of(PUBLIC_ID_NOT_FOUND));
    }
}
