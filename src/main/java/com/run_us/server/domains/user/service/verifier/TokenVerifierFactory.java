package com.run_us.server.domains.user.service.verifier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class TokenVerifierFactory {

  private final Map<String, TokenVerifier> verifiers = new ConcurrentHashMap<>();

  public TokenVerifierFactory(KakaoTokenVerifier kakaoTokenVerifier) {
    verifiers.put("KAKAO", kakaoTokenVerifier);
  }

  public TokenVerifier getVerifier(String provider) {
    TokenVerifier verifier = verifiers.get(provider.toUpperCase());
    if (verifier == null) {
      throw new IllegalArgumentException("Unsupported provider: " + provider);
    }
    return verifier;
  }
}
