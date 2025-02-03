package com.run_us.server.domains.running.controller.model.enums;

import static org.junit.jupiter.api.Assertions.*;

import com.run_us.server.domains.running.live.controller.model.SubscriptionTopic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("SubscriptionTopic 클래스의")
class SubscriptionTopicTest {

  @Test
  @DisplayName("RUNNING, QUEUE, LOG, ERROR enum이 존재한다")
  void it_has_RUNNING_QUEUE_LOG_ERROR_enum() {
    assertEquals(SubscriptionTopic.RUNNING, SubscriptionTopic.valueOf("RUNNING"));
    assertEquals(SubscriptionTopic.QUEUE, SubscriptionTopic.valueOf("QUEUE"));
    assertEquals(SubscriptionTopic.LOG, SubscriptionTopic.valueOf("LOG"));
    assertEquals(SubscriptionTopic.ERROR, SubscriptionTopic.valueOf("ERROR"));
  }

  @Test
  @DisplayName("from 메소드는 존재하지않는 토픽을 입력받으면 예외를 생성한다.")
  void it_throws_IllegalArgumentException_when_input_non_existing_topic() {
    assertThrows(IllegalArgumentException.class, () -> SubscriptionTopic.from("non-existing-topic"));
  }

  @Test
  @DisplayName("contains 메소드는 존재하지않는 토픽을 입력받으면 false를 반환한다.")
  void it_returns_false_when_input_non_existing_topic() {
    assertFalse(SubscriptionTopic.contains("non-existing-topic"));
  }
}