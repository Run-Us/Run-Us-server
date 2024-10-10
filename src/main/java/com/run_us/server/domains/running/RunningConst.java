package com.run_us.server.domains.running;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RunningConst {
  public static final String RUNNING_WS_SEND_PREFIX = "/topic/runnings/";
  public static final String RUNNING_WS_SUBSCRIBE_PATH = "runnings";
}
