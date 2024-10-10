package com.run_us.server.global.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SocketConst {
  public static final String WS_CONNECT_ENDPOINT = "/ws-hello";
  public static final String WS_DESTINATION_PREFIX_APP = "/app";
  public static final String WS_DESTINATION_PREFIX_TOPIC = "/topic";
  public static final String WS_DESTINATION_PREFIX_QUEUE = "/queue";

  public static final String USER_WS_SUBSCRIBE_PATH = "queue";
}
