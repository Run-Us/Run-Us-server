package com.run_us.server.global.common;

public final class GlobalConsts {
  public static final String TIME_ZONE_ID = "Asia/Seoul";
  public static final String DEFAULT_IMG_URL = "default_img_url";

  public static final String WS_CONNECT_ENDPOINT = "/ws-hello";
  public static final String WS_PUB_DESTINATION_PREFIX = "/app";
  public static final String WS_SUB_DESTINATION_PREFIX = "/topic";
  public static final String WS_SUB_QUEUE_DESTINATION_PREFIX = "/queue";
  public static final String RUNNING_WS_SEND_PREFIX = "/topic/runnings/";

  public static final String WS_USER_AUTH_HEADER = "user-id";
  public static final String SESSION_ATTRIBUTE_USER = "user-info";
}
