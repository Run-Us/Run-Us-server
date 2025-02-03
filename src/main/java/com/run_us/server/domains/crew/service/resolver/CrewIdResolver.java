package com.run_us.server.domains.crew.service.resolver;

import com.run_us.server.domains.crew.controller.model.enums.CrewErrorCode;
import com.run_us.server.domains.crew.controller.model.enums.CrewException;
import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewPrincipal;
import com.run_us.server.domains.crew.repository.CrewRepository;
import com.run_us.server.global.common.cache.InMemoryCache;
import com.run_us.server.global.common.resolver.DomainIdResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CrewIdResolver implements DomainIdResolver<CrewPrincipal> {
    private final CrewRepository crewRepository;
    private final InMemoryCache<String, CrewPrincipal> principalCache;

    private static final Duration CACHE_TTL = Duration.ofMinutes(30);

    @Override
    public CrewPrincipal resolve(String publicId) {
        return principalCache.get(publicId)
                .orElseGet(() -> loadAndCacheCrewPrincipal(publicId));
    }

    @Override
    public CrewPrincipal resolve(Integer internalId) {
        Crew crew = crewRepository.findById(internalId)
                .orElseThrow(() -> new CrewException(CrewErrorCode.CREW_NOT_FOUND));
        return new CrewPrincipal(crew.getPublicId(), internalId);
    }

    private CrewPrincipal loadAndCacheCrewPrincipal(String publicId) {
        Crew crew = crewRepository.findByPublicId(publicId)
                .orElseThrow(() -> new CrewException(CrewErrorCode.CREW_NOT_FOUND));

        CrewPrincipal principal = new CrewPrincipal(publicId, crew.getId());
        principalCache.put(publicId, principal, CACHE_TTL);
        return principal;
    }
}
