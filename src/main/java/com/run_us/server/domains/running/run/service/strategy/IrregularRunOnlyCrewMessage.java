package com.run_us.server.domains.running.run.service.strategy;

import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.running.run.domain.Run;

import java.time.format.DateTimeFormatter;

public class IrregularRunOnlyCrewMessage implements RunTopMessageStrategy {
    private final Crew crew;

    public IrregularRunOnlyCrewMessage(Crew crew) {
        this.crew = crew;
    }

    @Override
    public String createMessage() {
        String message = "\'" +
                crew.getCrewDescription().getTitle() +
                "\'번개런";

        return message;
    }
}
