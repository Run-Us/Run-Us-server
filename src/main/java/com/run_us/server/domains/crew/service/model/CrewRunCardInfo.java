package com.run_us.server.domains.crew.service.model;

import com.run_us.server.domains.crew.controller.model.response.RunCardInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CrewRunCardInfo {
    RunCardInfo regularRunCard;
    RunCardInfo irregularRunCard;

    @Builder
    public CrewRunCardInfo(RunCardInfo regularRunCard, RunCardInfo irregularRunCard) {
        this.regularRunCard = regularRunCard;
        this.irregularRunCard = irregularRunCard;
    }
}
