package com.run_us.server.global.security.resolver;

import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.exception.UserErrorCode;
import com.run_us.server.domains.user.exception.UserException;
import com.run_us.server.domains.user.repository.UserRepository;
import com.run_us.server.global.common.cache.InMemoryCache;
import com.run_us.server.global.security.principal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class UserIdResolver {
    private final InMemoryCache<String, UserPrincipal> principalCache;
    private final UserRepository userRepository;

    private static final Duration CACHE_TTL = Duration.ofMinutes(30);

    public UserPrincipal resolveUser(String publicId) {
        return principalCache.get(publicId)
                .orElseGet(() -> loadAndCacheUserPrincipal(publicId));
    }

    private UserPrincipal loadAndCacheUserPrincipal(String publicId) {
        User user = userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        UserPrincipal principal = new UserPrincipal(publicId, user.getId());
        principalCache.put(publicId, principal, CACHE_TTL);
        return principal;
    }
}
