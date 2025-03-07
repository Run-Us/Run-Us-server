package com.run_us.server.global.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.time.ZoneId;

import static com.run_us.server.global.common.SocketConst.WS_CONNECT_ENDPOINT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GlobalConst {
  public static final String TIME_ZONE_ID = "Asia/Seoul";
  public static final ZoneId ZONE_ID = ZoneId.of(TIME_ZONE_ID);
  public static final String TIME_FORMAT_PATTERN = "yyyy/MM/dd HH:mm:ss";

  public static final String DEFAULT_IMG_URL = "default_img_url";

  public static final String WS_USER_AUTH_HEADER = "user-id";
  public static final String SESSION_ATTRIBUTE_USER = "user-info";

  public static final Set<String> WHITE_LIST_PATHS = Set.of(
          "/test/auth",
          WS_CONNECT_ENDPOINT,
          "/auth"
  );
}
