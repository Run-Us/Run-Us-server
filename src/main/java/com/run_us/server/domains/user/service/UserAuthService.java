package com.run_us.server.domains.user.service;

import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.domain.Profile;
import com.run_us.server.domains.user.domain.OAuthInfo;
import com.run_us.server.domains.user.domain.TokenPair;
import com.run_us.server.domains.user.domain.AuthResult;
import com.run_us.server.domains.user.domain.AuthResultType;
import com.run_us.server.domains.user.domain.SocialProvider;
import com.run_us.server.domains.user.repository.OAuthInfoRepository;
import com.run_us.server.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserRepository userRepository;
    private final OAuthInfoRepository oAuthInfoRepository;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public AuthResult authenticateOAuth(String oAuthToken, SocialProvider provider) {
        try {
            String providerId = getProviderId(oAuthToken, provider);

            return findOAuthInfo(provider, providerId)
                    .map(oAuthInfo -> new AuthResult(AuthResultType.LOGIN_SUCCESS, login(oAuthInfo.getUser())))
                    .orElseGet(() -> new AuthResult(AuthResultType.SIGNUP_REQUIRED, null));
        } catch (Exception e) {
            return new AuthResult(AuthResultType.AUTH_FAILED, null);
        }
    }

    @Transactional
    public AuthResult signupAndLogin(String oAuthToken, SocialProvider provider, Profile profile) {
        try {
            String providerId = getProviderId(oAuthToken, provider);
            if (findOAuthInfo(provider, providerId).isPresent()) {
                return new AuthResult(AuthResultType.SIGNUP_FAILED, null);
            }

            User user = createAndSaveUser(profile);
            OAuthInfo oAuthInfo = createAndSaveOAuthInfo(provider, providerId, user);

            return new AuthResult(AuthResultType.LOGIN_SUCCESS, login(oAuthInfo.getUser()));
        } catch (Exception e) {
            return new AuthResult(AuthResultType.SIGNUP_FAILED, null);
        }
    }

    private TokenPair login(User user) {
        return jwtService.generateTokenPair(user);
    }

    private String getProviderId(String oAuthToken, SocialProvider provider) {
        return jwtService.getUserIdFromOAuthToken(oAuthToken, provider.name());
    }

    private Optional<OAuthInfo> findOAuthInfo(SocialProvider provider, String providerId) {
        return oAuthInfoRepository.findByProviderAndProviderId(provider, providerId);
    }

    private User createAndSaveUser(Profile profile) {
        User user = User.builder().build();
        user = userRepository.save(user);

        profile.setUserId(user.getId());
        user.setProfile(profile);

        return userRepository.save(user);
    }

    private OAuthInfo createAndSaveOAuthInfo(SocialProvider provider, String providerId, User user) {
        OAuthInfo oAuthInfo = OAuthInfo.builder()
                .provider(provider)
                .providerId(providerId)
                .user(user)
                .build();
        return oAuthInfoRepository.save(oAuthInfo);
    }

    /**
     * 개발용 로그인
     * (Only for development)
     *
     * @param userid 유저 내부 id
     * @return TokenPair
     * @throws IllegalArgumentException 유저가 없을 경우
     */
    @org.springframework.context.annotation.Profile("dev")
    @Transactional(readOnly = true)
    public TokenPair devLogin(Integer userid) {
        return userRepository.findByInternalId(userid)
                .map(this::login)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userid));
    }
}
