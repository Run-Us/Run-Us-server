package com.run_us.server.domains.running.live.controller.model;

import static com.run_us.server.domains.running.common.RunningConst.RUNNING_WS_SUBSCRIBE_PATH;
import static com.run_us.server.global.common.SocketConst.USER_WS_SUBSCRIBE_PATH;

import lombok.Getter;

@Getter
public enum SubscriptionTopic {
  RUNNING(RUNNING_WS_SUBSCRIBE_PATH),
  QUEUE(USER_WS_SUBSCRIBE_PATH),
  LOG("logs"),
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
