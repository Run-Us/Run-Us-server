package com.run_us.server.global.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GlobalConst {
  public static final String TIME_ZONE_ID = "Asia/Seoul";
  public static final ZoneId ZONE_ID = ZoneId.of(TIME_ZONE_ID);
  public static final String DEFAULT_IMG_URL = "default_img_url";

  public static final String WS_USER_AUTH_HEADER = "user-id";
  public static final String SESSION_ATTRIBUTE_USER = "user-info";
}
