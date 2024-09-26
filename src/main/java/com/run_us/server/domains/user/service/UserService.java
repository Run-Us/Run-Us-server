package com.run_us.server.domains.user.service;

import com.run_us.server.domains.user.model.User;
import com.run_us.server.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // TODO : publicId 로 유저 찾는 로직을 이거 하나로 몰아두는 게 어떤지 (예외처리 등을 한곳에서 관리하도록)
    /**
     * publicId 로 유저 정보를 가져오는 메서드
     * @param publicId
     * @return
     */
    public User getUserByPublicId(String publicId) {
        return userRepository.findByPublicId(publicId).orElseThrow(IllegalArgumentException::new); 
    }
}
