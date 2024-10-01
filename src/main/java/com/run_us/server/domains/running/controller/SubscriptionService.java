package com.run_us.server.domains.running.controller;

import com.run_us.server.domains.running.controller.model.enums.SubscriptionTopic;
import com.run_us.server.domains.running.service.RunningPreparationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private static final int TOPIC_INDEX = 2;
    private static final int SESSION_ID_INDEX = 3;
    private final RunningPreparationService runningPreparationService;

    public void process(String destination, String userId) {
      String[] destinationTokens = destination.split("/");

      switch (SubscriptionTopic.from(destinationTokens[TOPIC_INDEX])) {
        case RUNNING -> handleRunningTopic(destinationTokens[SESSION_ID_INDEX], userId);
        //TODO: unicast를 위한 queue 구현
        case QUEUE -> {}
      }
    }
    private void handleRunningTopic(String runningId, String userId) {
      runningPreparationService.joinRunning(runningId, userId);
    }
}
