package com.run_us.server.domains.user.service;

import com.run_us.server.domains.user.controller.model.request.UserSignUpRequest;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.domain.OAuthInfo;
import com.run_us.server.domains.user.domain.TokenPair;
import com.run_us.server.domains.user.domain.AuthResult;
import com.run_us.server.domains.user.domain.AuthResultType;
import com.run_us.server.domains.user.domain.SocialProvider;
import com.run_us.server.domains.user.repository.OAuthInfoRepository;
import com.run_us.server.domains.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

public class UserAuthService {
    private final UserRepository userRepository;
    private final OAuthInfoRepository oAuthInfoRepository;
    private final JwtService jwtService;

    public UserAuthService(UserRepository userRepository, OAuthInfoRepository oAuthInfoRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.oAuthInfoRepository = oAuthInfoRepository;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public AuthResult authenticateOAuth(String oAuthToken, SocialProvider provider, String expectedNonce) {
        try {
            String providerId = getProviderId(oAuthToken, provider, expectedNonce);
            return oAuthInfoRepository.findByProviderAndProviderId(String.valueOf(provider), providerId)
                    .map(oAuthInfo -> new AuthResult(AuthResultType.LOGIN_SUCCESS, login(oAuthInfo.getUser())))
                    .orElseGet(() -> new AuthResult(AuthResultType.SIGNUP_REQUIRED, null));
        } catch (Exception e) {
            return new AuthResult(AuthResultType.AUTH_FAILED, null);
        }
    }

    @Transactional
    public AuthResult signupAndLogin(String oAuthToken, SocialProvider provider, String expectedNonce, UserSignUpRequest userSignUpRequest) {
        try {
            String providerId = getProviderId(oAuthToken, provider, expectedNonce);

            User user = createAndSaveUser(userSignUpRequest);
            OAuthInfo oAuthInfo = createAndSaveOAuthInfo(provider, providerId, user);

            return new AuthResult(AuthResultType.LOGIN_SUCCESS, login(oAuthInfo.getUser()));
        } catch (Exception e) {
            return new AuthResult(AuthResultType.SIGNUP_FAILED, null);
        }
    }

    private TokenPair login(User user) {
        return jwtService.generateTokenPair(user);
    }

    private String getProviderId(String oAuthToken, SocialProvider provider, String expectedNonce) {
        return jwtService.getUserIdFromOAuthToken(oAuthToken, provider.name(), expectedNonce);
    }

    private User createAndSaveUser(UserSignUpRequest userSignUpRequest) {
        User user = User.builder()
                .nickname(userSignUpRequest.getNickName())
                .build();
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
}
