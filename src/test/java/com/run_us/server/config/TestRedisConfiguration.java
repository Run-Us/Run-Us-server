package com.run_us.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;

import java.io.IOException;
import java.io.Serializable;

@TestConfiguration
@Profile("test")
@EnableRedisRepositories
@Import(EmbeddedRedisConfiguration.class)
public class TestRedisConfiguration {

  private final EmbeddedRedisConfiguration testRedisConfiguration;

  @Value("${spring.data.redis.host}")
  private String redisHost;

  private final int redisPort;

  public TestRedisConfiguration(EmbeddedRedisConfiguration testRedisConfiguration) {
    this.testRedisConfiguration = testRedisConfiguration;
    this.redisPort = testRedisConfiguration.getRedisPort();
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(redisHost);
    redisStandaloneConfiguration.setPort(redisPort);
    return new LettuceConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  public RedisTemplate<String, String> redisTemplate() {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    return redisTemplate;
  }

  @Bean
  public RedisTemplate<String, Serializable> serializableRedisTemplate() {
    RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericToStringSerializer<>(String.class));
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    return redisTemplate;
  }

  @Bean
  public DefaultRedisScript<Boolean> updateLocationScript() throws IOException {
    ScriptSource source = new ResourceScriptSource(new ClassPathResource("META-INF/scripts/update_location.lua"));
    return new DefaultRedisScript<>(source.getScriptAsString(), Boolean.class);
  }
}
