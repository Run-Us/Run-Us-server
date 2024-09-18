package com.run_us.server.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;

@DisplayName("Test Redis Configuration")
@Profile("test")
@TestConfiguration
public class EmbeddedRedisConfiguration {

  private final RedisServer redisServer;
  private final int redisPort;

  public EmbeddedRedisConfiguration() throws IOException {
    this.redisPort = findAvailablePort();
    this.redisServer = new RedisServer(redisPort);
  }

  private int findAvailablePort() throws IOException {
    for (int port = 10000; port <= 65535; port++) {
      Process process = executeGrepProcessCommand(port);
      if (!isRunning(process)) {
        return port;
      }
    }
    throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
  }

  private Process executeGrepProcessCommand(int port) throws IOException {
    String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
    String[] shell = {"/bin/sh", "-c", command};
    return Runtime.getRuntime().exec(shell);
  }

  private boolean isRunning(Process process) {
    String line;
    StringBuilder pidInfo = new StringBuilder();

    try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
      while ((line = input.readLine()) != null) {
        pidInfo.append(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return StringUtils.hasText(pidInfo.toString());
  }

  @PostConstruct
  public void postConstruct() throws IOException {
    redisServer.start();
  }

  @PreDestroy
  public void preDestroy() throws IOException {
    redisServer.stop();
  }

  public int getRedisPort() {
    return redisPort;
  }

}
