package com.run_us.server.v2.running.common;

import java.security.SecureRandom;
import java.util.Random;

public final class PassCodeGenerator {

  private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final int CHARSET_SIZE = 36;
  private static final int CODE_LEN = 4;

  public static String generatePassCode() {
    StringBuilder sb = new StringBuilder();
    // create random object every call to ensure randomness based on system-clock
    Random random = new SecureRandom();
    for(int i = 0; i < CODE_LEN; i++) {
      sb.append(CHARSET.charAt(random.nextInt(CHARSET_SIZE)));
    }
    return sb.toString();
  }
}
