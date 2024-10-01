package com.run_us.server.domains.running.controller.model.enums;

import lombok.Getter;

@Getter
public enum SubscriptionTopic {

  RUNNING("running"),
  QUEUE("queue"),
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
