package com.run_us.server.domains.user.repository;

import com.run_us.server.domains.user.domain.SocialProvider;
import com.run_us.server.domains.user.domain.TokenStatus;
import com.run_us.server.global.common.cache.CacheEntry;
import com.run_us.server.global.common.cache.InMemoryCache;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
public class OAuthTokenRepository {
    private final InMemoryCache<String, TokenStatus> tokenCache;

    public OAuthTokenRepository(InMemoryCache<String, TokenStatus> tokenCache) {
        this.tokenCache = tokenCache;
    }

    public void storeNonce(SocialProvider provider, String sub, String nonce, TokenStatus status, Duration ttl) {
        String key = generateKey(provider, sub, nonce);
        tokenCache.put(key, status, ttl);
    }

    public Optional<TokenStatus> getNonceStatus(SocialProvider provider, String sub, String nonce) {
        String key = generateKey(provider, sub, nonce);
        return tokenCache.get(key);
    }

    public void updateNonceStatus(SocialProvider provider, String sub, String nonce, TokenStatus status) {
        String key = generateKey(provider, sub, nonce);
        Optional<CacheEntry<TokenStatus>> existingEntry = tokenCache.getEntry(key);

        if (existingEntry.isPresent()) {
            CacheEntry<TokenStatus> entry = existingEntry.get();
            tokenCache.remove(key);
            if (entry.expiresAt() != null) {
                Duration remainingTtl = Duration.between(java.time.Instant.now(), entry.expiresAt());
                tokenCache.put(key, status, remainingTtl);
            } else {
                tokenCache.put(key, status);
            }
        } else {
            throw new IllegalStateException("Token not found for key: " + key);
        }
    }

    private String generateKey(SocialProvider provider, String sub, String nonce) {
        return String.format("auth:%s:%s:%s", provider.name(), sub, nonce);
    }
}
