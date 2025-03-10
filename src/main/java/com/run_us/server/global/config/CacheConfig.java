package com.run_us.server.global.config;

import com.run_us.server.domains.crew.domain.CrewPrincipal;
import com.run_us.server.domains.user.domain.UserPrincipal;
import com.run_us.server.global.common.cache.InMemoryCache;
import com.run_us.server.global.common.cache.RedisInMemoryCache;
import com.run_us.server.global.common.cache.SpringInMemoryCache;
import com.run_us.server.domains.user.domain.TokenStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Duration;

@Configuration
public class CacheConfig {

    @Value("${cache.cleanup.interval:300}")
    private int cleanupIntervalSeconds;

    @Value("${cache.cleanup.thread-pool-size:1}")
    private int cleanupThreadPoolSize;

    @Bean
    public TaskScheduler cacheCleanupScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(cleanupThreadPoolSize);
        scheduler.setThreadNamePrefix("cache-cleanup-");
        scheduler.setDaemon(true);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setAwaitTerminationSeconds(10);
        return scheduler;
    }

    @Bean
    public InMemoryCache<String, TokenStatus> tokenStatusCache(TaskScheduler cacheCleanupScheduler) {
        return new SpringInMemoryCache<>(
                cacheCleanupScheduler,
                Duration.ofSeconds(cleanupIntervalSeconds)
        );
    }

    @Bean
    public InMemoryCache<String, UserPrincipal> userPrincipalCache(
        TaskScheduler cacheCleanupScheduler
    ) {
        return new SpringInMemoryCache<>(
            cacheCleanupScheduler,
            Duration.ofSeconds(cleanupIntervalSeconds)
        );
    }

    @Bean
    public InMemoryCache<String, CrewPrincipal> crewPrincipalCache(
        TaskScheduler cacheCleanupScheduler
    ) {
        return new SpringInMemoryCache<>(
            cacheCleanupScheduler,
            Duration.ofSeconds(cleanupIntervalSeconds)
        );
    }

    @Bean
    public InMemoryCache<String, String> generalStringCache(
        RedisTemplate<String, String> redisTemplate
    ) {
        return new RedisInMemoryCache<>(redisTemplate);
    }
}