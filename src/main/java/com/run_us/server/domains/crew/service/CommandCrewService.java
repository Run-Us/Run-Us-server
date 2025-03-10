package com.run_us.server.domains.crew.service;

import com.run_us.server.domains.crew.controller.model.request.CreateCrewRequest;
import com.run_us.server.domains.crew.controller.model.request.UpdateCrewJoinTypeRequest;
import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewDescription;
import com.run_us.server.domains.crew.repository.CrewRepository;
import com.run_us.server.domains.crew.service.model.UpdateCrewInfo;
import com.run_us.server.domains.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandCrewService {

    private final CrewRepository crewRepository;
    private final CrewValidator crewValidator;

    public Crew saveCrew(CreateCrewRequest requestDto, User creator) {
        Crew crew = requestDto.toEntity(creator);
        return crewRepository.save(crew);
    }

    public void updateCrewInfo(UpdateCrewInfo requestDto, Crew crew, Integer userId) {
        crewValidator.validateCrewOwner(crew, userId);

        CrewDescription newCrewDescription = requestDto.to(crew.getCrewDescription());
        crew.updateCrewInfo(newCrewDescription);
    }

    public void updateCrewJoinRule(UpdateCrewJoinTypeRequest requestDto, Crew crew, Integer userId) {
        crewValidator.validateCrewOwner(crew, userId);

        crew.updateJoinRule(requestDto.getJoinType(), requestDto.getJoinQuestion());
    }

    public void closeCrew(Crew crew, Integer userId) {
        crewValidator.validateCrewOwner(crew, userId);

        crew.close();
    }
}
