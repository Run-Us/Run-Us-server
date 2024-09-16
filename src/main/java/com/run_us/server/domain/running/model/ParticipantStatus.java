package com.run_us.server.domain.running.model;

public enum ParticipantStatus {
    READY,
    RUN,
    PAUSE,
    END;

    public boolean isActive() {
        return this == RUN || this == PAUSE;
    }

    public boolean isFinished() {
        return this == END;
    }
}