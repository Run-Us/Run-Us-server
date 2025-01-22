package com.run_us.server.domains.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.run_us.server.domains.user.domain.TokenPair;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.verifier.TokenVerifierFactory;
import com.run_us.server.global.common.cache.InMemoryCache;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

  private static final String ISSUER = "RunUSAuthService";
  private final TokenVerifierFactory tokenVerifierFactory;
  private final InMemoryCache<String, String> refreshTokenCache;
  @Value("${jwt.secret}")
  private String jwtSecret;
  @Value("${jwt.expiration}")
  private long jwtExpiration;
  @Value("${jwt.refresh.expiration}")
  private long jwtRefreshExpiration;

  public String generateAccessToken(User user) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpiration);

    return JWT.create()
        .withSubject(user.getPublicId())
        .withIssuedAt(now)
        .withExpiresAt(expiryDate)
        .withIssuer(ISSUER)
        .sign(Algorithm.HMAC256(jwtSecret));
  }

  public String generateRefreshToken(User user) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtRefreshExpiration);

    String refreshToken = JWT.create()
                            .withSubject(user.getPublicId())
                            .withIssuedAt(now)
                            .withExpiresAt(expiryDate)
                            .withIssuer(ISSUER)
                            .withClaim("tokenType", "refresh")
                            .sign(Algorithm.HMAC256(jwtSecret));

    refreshTokenCache.put("auth:refresh:"+user.getPublicId(), refreshToken);
    return refreshToken;
  }

  public boolean nonceRefreshToken(String refreshToken) {
    String userPublicId = getUserIdFromAccessToken(refreshToken);
    return refreshTokenCache.remove("auth:refresh:"+userPublicId);
  }

  public TokenPair generateTokenPair(User user) {
    String accessToken = generateAccessToken(user);
    String refreshToken = generateRefreshToken(user);
    return new TokenPair(accessToken, refreshToken);
  }

  public DecodedJWT verifyAccessToken(String token) throws JWTVerificationException {
    return JWT.require(Algorithm.HMAC256(jwtSecret)).withIssuer(ISSUER).build().verify(token);
  }

  public String getUserIdFromAccessToken(String token) throws JWTVerificationException {
    DecodedJWT jwt = verifyAccessToken(token);
    return jwt.getSubject();
  }

  public DecodedJWT verifyOAuthToken(String token, String provider)
      throws JWTVerificationException {
    return tokenVerifierFactory.getVerifier(provider).verifyToken(token);
  }

  public String getUserIdFromOAuthToken(String token, String provider)
      throws JWTVerificationException {
    DecodedJWT jwt = verifyOAuthToken(token, provider);
    return jwt.getSubject();
  }
}
