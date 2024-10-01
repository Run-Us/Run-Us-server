package com.run_us.server.domains.running.controller.model.enums;

import static com.run_us.server.global.common.GlobalConsts.RUNNING_WS_SUBSCRIBE_PATH;
import static com.run_us.server.global.common.GlobalConsts.USER_WS_SUBSCRIBE_PATH;

import lombok.Getter;

@Getter
public enum SubscriptionTopic {

  RUNNING(RUNNING_WS_SUBSCRIBE_PATH),
  QUEUE(USER_WS_SUBSCRIBE_PATH),
  ERROR("error");

  private final String topic;

  SubscriptionTopic(String topic) {
    this.topic = topic;
  }

  public static SubscriptionTopic from(String topic) {
    for (SubscriptionTopic subscriptionTopic : SubscriptionTopic.values()) {
      if (subscriptionTopic.getTopic().equals(topic)) {
        return subscriptionTopic;
      }
    }
    throw new IllegalArgumentException("Invalid topic: " + topic);
  }

  public static boolean contains(String topic) {
    for (SubscriptionTopic subscriptionTopic : SubscriptionTopic.values()) {
      if (subscriptionTopic.getTopic().equals(topic)) {
        return true;
      }
    }
    return false;
  }
}
