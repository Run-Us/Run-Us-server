package com.run_us.server.domains.user.repository;

import com.run_us.server.domains.user.domain.SocialProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class OAuthRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public OAuthRedisRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * OIDC 토큰의 nonce를 검증하고 저장
     *
     * @param provider OIDC 제공자
     * @param sub OIDC 사용자 식별자
     * @param nonce 검증할 nonce
     * @param expiration 만료 시간
     * @return nonce가 처음 사용되는 경우 true, 이미 사용된 경우 false
     */
    public boolean validateAndStoreNonce(SocialProvider provider, String sub, String nonce, Duration expiration) {
        String redisKey = provider.name() + ":" + sub;
        return Boolean.TRUE.equals(
                redisTemplate.opsForValue()
                        .setIfAbsent(redisKey, nonce, expiration));
    }
}
