package com.run_us.server.domains.user.service.verifier;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoTokenVerifier implements TokenVerifier {

    private final String appKey;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private Map<String, RSAPublicKey> publicKeys = new HashMap<>();

    public KakaoTokenVerifier(@Value("${spring.security.oauth2.client.registration.kakao.client-id}") String appKey) {
        this.appKey = appKey;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
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

                byte[] modulusBytes = Base64.getUrlDecoder().decode(n);

                RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                        .generatePublic(new X509EncodedKeySpec(modulusBytes));

                publicKeys.put(kid, publicKey);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to refresh public keys", e);
        }
    }

    @Override
    public DecodedJWT verifyToken(String token, String expectedNonce) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String kid = jwt.getKeyId();
            RSAPublicKey publicKey = publicKeys.get(kid);

            if (publicKey == null) {
                throw new JWTVerificationException("Unable to find appropriate key");
            }

            Algorithm algorithm = Algorithm.RSA256(publicKey, null);

            DecodedJWT verifiedJWT = JWT.require(algorithm)
                    .withIssuer("https://kauth.kakao.com")
                    .withAudience(appKey)
                    .withClaim("nonce", expectedNonce)
                    .build()
                    .verify(token);

            String tokenNonce = verifiedJWT.getClaim("nonce").asString();
            if (!expectedNonce.equals(tokenNonce)) {
                throw new JWTVerificationException("Nonce mismatch");
            }

            return verifiedJWT;
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("ID token verification failed", exception);
        }
    }
}