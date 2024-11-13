package com.run_us.server.domains.user.service.verifier;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.run_us.server.domains.user.domain.SocialProvider;
import com.run_us.server.domains.user.domain.TokenStatus;
import com.run_us.server.domains.user.repository.OAuthTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class KakaoTokenVerifier implements TokenVerifier {

    private final String appKey;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final OAuthTokenRepository oauthTokenRepository;
    private Map<String, RSAPublicKey> publicKeys = new HashMap<>();

    public KakaoTokenVerifier(@Value("${spring.security.oauth2.client.registration.kakao.client-id}") String appKey, OAuthTokenRepository oauthTokenRepository) {
        this.appKey = appKey;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.oauthTokenRepository = oauthTokenRepository;
        refreshPublicKeys();
    }

    @Scheduled(fixedRate = 3600000)
    public void refreshPublicKeys() {
        String jwksUrl = "https://kauth.kakao.com/.well-known/jwks.json";
        String jwksResponse = restTemplate.getForObject(jwksUrl, String.class);
        try {
            JsonNode jwks = objectMapper.readTree(jwksResponse);
            for (JsonNode key : jwks.get("keys")) {
                String kid = key.get("kid").asText();
                String n = key.get("n").asText();
                String e = key.get("e").asText();

                RSAPublicKey publicKey = createPublicKey(n, e);

                publicKeys.put(kid, publicKey);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to refresh public keys", e);
        }
    }

    private RSAPublicKey createPublicKey(String modulusBase64, String exponentBase64) throws Exception {
        byte[] modulusBytes = Base64.getUrlDecoder().decode(modulusBase64);
        byte[] exponentBytes = Base64.getUrlDecoder().decode(exponentBase64);

        BigInteger modulus = new BigInteger(1, modulusBytes);
        BigInteger exponent = new BigInteger(1, exponentBytes);

        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) factory.generatePublic(spec);
    }

    @Override
    public DecodedJWT verifyToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            String nonce = jwt.getClaim("nonce").asString();
            if (nonce == null) {
                throw new JWTVerificationException("Token nonce is missing");
            }

            String sub = jwt.getSubject();
            Optional<TokenStatus> existingEntry =
                    oauthTokenRepository.getNonceStatus(SocialProvider.KAKAO, sub, nonce);
            if (existingEntry.isPresent() && existingEntry.get() == TokenStatus.USED) {
                throw new JWTVerificationException("Token has already been used");
            }
            Duration ttl = Duration.between(Instant.now(), jwt.getExpiresAt().toInstant());
            oauthTokenRepository.storeNonce(SocialProvider.KAKAO, sub, nonce, TokenStatus.PENDING, ttl);

            String kid = jwt.getKeyId();
            RSAPublicKey publicKey = publicKeys.get(kid);

            if (publicKey == null) {
                throw new JWTVerificationException("Unable to find appropriate key");
            }

            Algorithm algorithm = Algorithm.RSA256(publicKey, null);

            return JWT.require(algorithm)
                    .withIssuer("https://kauth.kakao.com")
                    .withAudience(appKey)
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("ID token verification failed", exception);
        }
    }
}