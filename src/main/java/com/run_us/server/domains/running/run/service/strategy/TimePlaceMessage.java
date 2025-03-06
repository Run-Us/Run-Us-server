package com.run_us.server.domains.running.run.service.strategy;

import com.run_us.server.domains.running.run.domain.Run;

import java.time.format.DateTimeFormatter;

public class TimePlaceMessage implements RunTopMessageStrategy {
    private final Run run;

    public TimePlaceMessage(Run run) {
        this.run = run;
    }

    @Override
    public String createMessage() {
        return run.getPreview().getBeginTime().format(DateTimeFormatter.ofPattern("M월 dd일 E요일 a h:mm"));
    }
}
