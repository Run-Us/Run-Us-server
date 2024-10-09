package com.run_us.server.domains.running.controller.model.enums;

import static com.run_us.server.global.common.GlobalConst.RUNNING_WS_SUBSCRIBE_PATH;
import static com.run_us.server.global.common.GlobalConst.USER_WS_SUBSCRIBE_PATH;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("SubscriptionTopic 클래스의")
class SubscriptionTopicTest {

  @Nested
  @DisplayName("from 메소드는")
  class Describe_from {

    @Nested
    @DisplayName("존재하는 topic을 입력받았을 때")
    class Context_with_existing_topic {

      @DisplayName("해당 topic을 반환한다")
      @Test
      void it_returns_the_topic() {
        assertEquals(SubscriptionTopic.RUNNING, SubscriptionTopic.from(RUNNING_WS_SUBSCRIBE_PATH));
        assertEquals(SubscriptionTopic.QUEUE, SubscriptionTopic.from(USER_WS_SUBSCRIBE_PATH));
        assertEquals(SubscriptionTopic.ERROR, SubscriptionTopic.from("error"));
      }
    }

    @Nested
    @DisplayName("존재하지 않는 topic을 입력받았을 때")
    class Context_with_non_existing_topic {

      @DisplayName("IllegalArgumentException을 던진다")
      @Test
      void it_throws_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> SubscriptionTopic.from("non-existing-topic"));
      }
    }
  }

  @Nested
  @DisplayName("contains 메소드는")
  class Describe_contains {

    @Nested
    @DisplayName("존재하는 topic을 입력받았을 때")
    class Context_with_existing_topic {

      @DisplayName("true를 반환한다")
      @Test
      void it_returns_true() {
        assertTrue(SubscriptionTopic.contains(RUNNING_WS_SUBSCRIBE_PATH));
        assertTrue(SubscriptionTopic.contains(USER_WS_SUBSCRIBE_PATH));
        assertTrue(SubscriptionTopic.contains("error"));
      }
    }

    @Nested
    @DisplayName("존재하지 않는 topic을 입력받았을 때")
    class Context_with_non_existing_topic {

      @DisplayName("false를 반환한다")
      @Test
      void it_returns_false() {
        assertFalse(SubscriptionTopic.contains("non-existing-topic"));
      }
    }
  }

}