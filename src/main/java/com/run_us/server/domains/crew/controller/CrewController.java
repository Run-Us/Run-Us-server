package com.run_us.server.domains.crew.controller;

import com.run_us.server.domains.crew.controller.model.request.CreateCrewRequest;
import com.run_us.server.domains.crew.controller.model.response.CrewHttpResponseCode;
import com.run_us.server.domains.crew.controller.model.response.CreateCrewResponse;
import com.run_us.server.domains.crew.service.usecase.CreateCrewUseCase;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/crews")
public class CrewController {
    private final CreateCrewUseCase createCrewUseCase;

    @PostMapping
    public ResponseEntity<SuccessResponse<CreateCrewResponse>> createCrew(
            @RequestBody CreateCrewRequest requestDto,
            @RequestAttribute("publicUserId") String userId) {
        log.info("action=create_crew user_id={}", userId);

        SuccessResponse<CreateCrewResponse> response = createCrewUseCase.createCrew(requestDto, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
