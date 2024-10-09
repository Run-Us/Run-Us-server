package com.run_us.server.global.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GlobalConst {
  public static final String TIME_ZONE_ID = "Asia/Seoul";
  public static final String DEFAULT_IMG_URL = "default_img_url";

  public static final String WS_CONNECT_ENDPOINT = "/ws-hello";
  public static final String WS_DESTINATION_PREFIX_APP = "/app";
  public static final String WS_DESTINATION_PREFIX_TOPIC = "/topic";
  public static final String WS_DESTINATION_PREFIX_QUEUE = "/queue";

  public static final String RUNNING_WS_SEND_PREFIX = "/topic/runnings/";
  public static final String RUNNING_WS_SUBSCRIBE_PATH = "runnings";
  public static final String USER_WS_SUBSCRIBE_PATH = "queue";

  public static final String WS_USER_AUTH_HEADER = "user-id";
  public static final String SESSION_ATTRIBUTE_USER = "user-info";
}
