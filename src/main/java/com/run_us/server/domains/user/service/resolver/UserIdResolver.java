package com.run_us.server.domains.user.service.resolver;

import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.domain.UserPrincipal;
import com.run_us.server.domains.user.exception.UserErrorCode;
import com.run_us.server.domains.user.exception.UserException;
import com.run_us.server.domains.user.repository.UserRepository;
import com.run_us.server.global.common.cache.InMemoryCache;
import com.run_us.server.global.common.resolver.DomainIdResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserIdResolver implements DomainIdResolver<UserPrincipal> {
    private final UserRepository userRepository;
    private final InMemoryCache<String, UserPrincipal> principalCache;

    private static final Duration CACHE_TTL = Duration.ofMinutes(30);

    @Override
    public UserPrincipal resolve(String publicId) {
        return principalCache.get(publicId)
                .orElseGet(() -> loadAndCacheUserPrincipal(publicId));
    }

    @Override
    public UserPrincipal resolve(Integer internalId) {
        User user = userRepository.findById(internalId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        return new UserPrincipal(user.getPublicId(), internalId);
    }

    private UserPrincipal loadAndCacheUserPrincipal(String publicId) {
        User user = userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        UserPrincipal principal = new UserPrincipal(publicId, user.getId());
        principalCache.put(publicId, principal, CACHE_TTL);
        return principal;
    }
}
