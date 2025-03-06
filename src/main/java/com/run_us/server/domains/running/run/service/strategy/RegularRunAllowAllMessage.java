package com.run_us.server.domains.running.run.service.strategy;

public class RegularRunAllowAllMessage implements RunTopMessageStrategy {
    @Override
    public String createMessage() {
        return "게스트 모집";
    }
}
