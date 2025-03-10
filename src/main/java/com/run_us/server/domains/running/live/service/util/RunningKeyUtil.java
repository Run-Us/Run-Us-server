package com.run_us.server.domains.running.live.service.util;

import com.run_us.server.domains.running.common.RunningConst;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RunningKeyUtil {
  public static String createLiveKey(String runningId, String userId, String suffix) {
    return RunningConst.RUNNING_PREFIX + runningId + ":" + userId + suffix;
  }

  public static String extractUserIdFromKey(String key) {
    String[] parts = key.split(":");
    return parts.length >= 3 ? parts[2] : null;
  }
}
