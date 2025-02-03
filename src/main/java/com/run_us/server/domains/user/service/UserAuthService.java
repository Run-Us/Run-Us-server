package com.run_us.server.domains.user.service;

import com.run_us.server.domains.user.domain.AuthResult;
import com.run_us.server.domains.user.domain.AuthResultType;
import com.run_us.server.domains.user.domain.OAuthInfo;
import com.run_us.server.domains.user.domain.OAuthToken;
import com.run_us.server.domains.user.domain.Profile;
import com.run_us.server.domains.user.domain.SocialProvider;
import com.run_us.server.domains.user.domain.TokenPair;
import com.run_us.server.domains.user.domain.TokenStatus;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.exception.UserAuthException;
import com.run_us.server.domains.user.exception.UserErrorCode;
import com.run_us.server.domains.user.exception.UserException;
import com.run_us.server.domains.user.repository.OAuthInfoRepository;
import com.run_us.server.domains.user.repository.OAuthTokenRepository;
import com.run_us.server.domains.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAuthService {
  private final UserRepository userRepository;
  private final OAuthInfoRepository oAuthInfoRepository;
  private final OAuthTokenRepository oAuthTokenRepository;
  private final JwtService jwtService;
  private final UserService userService;

  @Transactional(readOnly = true)
  public AuthResult authenticateOAuth(String rawToken, SocialProvider provider) {
    OAuthToken oAuthToken = OAuthToken.from(rawToken, provider);
    String providerId = getProviderId(oAuthToken.getToken(), provider);
    OAuthInfo oAuthInfo =
        findOAuthInfo(provider, providerId)
            .orElseThrow(() -> UserException.of(UserErrorCode.USER_NOT_FOUND));
    return proceedLogin(oAuthToken, oAuthInfo.getUser());
  }

  @Transactional
  public AuthResult signupAndLogin(String rawToken, SocialProvider provider, Profile profile) {
    try {
      OAuthToken oAuthToken = OAuthToken.from(rawToken, provider);
      String providerId = getProviderId(oAuthToken.getToken(), provider);
      if (findOAuthInfo(provider, providerId).isPresent()) {
        throw UserException.of(UserErrorCode.USER_ALREADY_EXISTS);
      }
      User user = createAndSaveUser(profile);
      createAndSaveOAuthInfo(provider, providerId, user);
      return proceedLogin(oAuthToken, user);
    } catch (Exception e) {
      throw UserAuthException.of(UserErrorCode.SIGNUP_FAILED);
    }
  }

  @Transactional(readOnly = true)
  public AuthResult refresh(String refreshToken) {
    if (!jwtService.nonceRefreshToken(refreshToken)) {
      throw UserAuthException.of(UserErrorCode.REFRESH_FAILED);
    }

    String userPublicId = jwtService.getUserIdFromAccessToken(refreshToken);

    User user = userService.getUserByPublicId(userPublicId);
    if (user == null) {
      throw UserAuthException.of(UserErrorCode.USER_NOT_FOUND);
    }

    return new AuthResult(AuthResultType.REFRESH_SUCCESS, login(user));
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
    OAuthInfo oAuthInfo =
        OAuthInfo.builder().provider(provider).providerId(providerId).user(user).build();
    return oAuthInfoRepository.save(oAuthInfo);
  }

  private AuthResult proceedLogin(OAuthToken oAuthToken, User user) {
    TokenPair tokenPair = login(user);
    oAuthTokenRepository.updateNonceStatus(
        oAuthToken.getProvider(), oAuthToken.getSub(), oAuthToken.getNonce(), TokenStatus.USED);
    return new AuthResult(AuthResultType.LOGIN_SUCCESS, tokenPair);
  }

  /**
   * 개발용 로그인 (Only for development)
   *
   * @param userid 유저 내부 id
   * @return TokenPair
   * @throws IllegalArgumentException 유저가 없을 경우
   */
  @org.springframework.context.annotation.Profile("dev")
  @Transactional(readOnly = true)
  public TokenPair devLogin(Integer userid) {
    return userRepository
        .findByInternalId(userid)
        .map(this::login)
        .orElseThrow(() -> new IllegalArgumentException("User not found: " + userid));
  }
}
