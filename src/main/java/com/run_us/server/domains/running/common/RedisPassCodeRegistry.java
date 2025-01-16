package com.run_us.server.domains.running.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisPassCodeRegistry implements PassCodeRegistry{

  private final RedisTemplate<String, String> redisTemplate;
  private static final int MAX_RETRIES = 10;

  @Override
  public String generateAndGetPassCode(String runId) {
    String passCode;
    int retries = 0;
    if(getRunIdByPassCode(runId).isPresent()) {
      throw RunningException.of(RunningErrorCode.LIVE_RUNNING_ALREADY_CREATED);
    }
    // 최대 재시도 횟수를 넘어가면 예외를 발생시킨다.
    // 초당 방 생성 요청 62개이상, 활성화된 입장코드가 68만개 이상일때
    // 10번 재시도 실패확률이 1%이상으로 예상되며 이때 예외를 발생시킨다.
    while (retries < MAX_RETRIES) {
      passCode = PassCodeGenerator.generatePassCode();
      if (Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(passCode, runId, 3, TimeUnit.HOURS))) {
        return passCode;
      }
      retries++;
    }
    throw new IllegalStateException("Failed to generate pass code");
  }

  @Override
  public void unregisterPassCode(String passCode) {
    redisTemplate.delete(passCode);
  }

  @Override
  public Optional<String> getRunIdByPassCode(String passCode) {
    return Optional.ofNullable(redisTemplate.opsForValue().get(passCode));
  }
}
