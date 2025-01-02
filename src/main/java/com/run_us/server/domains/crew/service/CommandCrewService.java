package com.run_us.server.domains.crew.service;

import com.run_us.server.domains.crew.controller.model.enums.CrewErrorCode;
import com.run_us.server.domains.crew.controller.model.enums.CrewException;
import com.run_us.server.domains.crew.controller.model.request.CreateCrewRequest;
import com.run_us.server.domains.crew.controller.model.request.UpdateCrewInfoRequest;
import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewDescription;
import com.run_us.server.domains.crew.repository.CrewRepository;
import com.run_us.server.domains.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandCrewService {

    private final CrewRepository crewRepository;

    public Crew saveCrew(CreateCrewRequest requestDto, User creator) {
        Crew crew = requestDto.toEntity(creator);
        return crewRepository.save(crew);
    }

    public void updateCrewInfo(UpdateCrewInfoRequest requestDto, Crew crew, Integer userId) {

        if (!crew.isOwner(userId)) {
            throw new CrewException(CrewErrorCode.FORBIDDEN_UPDATE_CREW);
        }

        CrewDescription newCrewDescription = requestDto.from(crew.getCrewDescription());
        crew.updateCrewInfo(newCrewDescription);
    }
}
