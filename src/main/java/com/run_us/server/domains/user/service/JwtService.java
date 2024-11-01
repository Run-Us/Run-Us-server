package com.run_us.server.domains.user.service;

import com.run_us.server.domains.user.domain.TokenPair;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.verifier.TokenVerifierFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh.expiration}")
    private long jwtRefreshExpiration;

    private final TokenVerifierFactory tokenVerifierFactory;

    private static final String ISSUER = "RunUSAuthService";

    public JwtService(TokenVerifierFactory tokenVerifierFactory) {
        this.tokenVerifierFactory = tokenVerifierFactory;
    }

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

        return JWT.create()
                .withSubject(user.getPublicId())
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .withIssuer(ISSUER)
                .withClaim("tokenType", "refresh")
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public TokenPair generateTokenPair(User user) {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);
        return new TokenPair(accessToken, refreshToken);
    }

    public DecodedJWT verifyAccessToken(String token) throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC256(jwtSecret))
                .withIssuer(ISSUER)
                .build()
                .verify(token);
    }

    public String getUserIdFromAccessToken(String token) throws JWTVerificationException {
        DecodedJWT jwt = verifyAccessToken(token);
        return jwt.getSubject();
    }

    public DecodedJWT verifyOAuthToken(String token, String provider) throws JWTVerificationException {
        return tokenVerifierFactory.getVerifier(provider).verifyToken(token);
    }

    public String getUserIdFromOAuthToken(String token, String provider) throws JWTVerificationException {
        DecodedJWT jwt = verifyOAuthToken(token, provider);
        return jwt.getSubject();
    }
}