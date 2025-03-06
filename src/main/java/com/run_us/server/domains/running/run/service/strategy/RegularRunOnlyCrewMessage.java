package com.run_us.server.domains.running.run.service.strategy;

import com.run_us.server.domains.crew.domain.Crew;

public class RegularRunOnlyCrewMessage implements RunTopMessageStrategy {
    private final Crew crew;

    public RegularRunOnlyCrewMessage(Crew crew) {
        this.crew = crew;
    }

    @Override
    public String createMessage() {
        String message = "\'" +
                crew.getCrewDescription().getTitle() +
                "\'정기런";
        
        return message;
    }
}
