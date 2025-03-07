package com.run_us.server.domains.running.run.service.strategy;

public class RunTopMessageContext {

    public static String getMessage(RunTopMessageStrategy strategy) {
        return strategy.createMessage();
    }
}
