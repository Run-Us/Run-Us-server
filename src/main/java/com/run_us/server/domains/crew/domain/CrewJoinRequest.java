package com.run_us.server.domains.crew.domain;

import com.run_us.server.domains.crew.controller.model.enums.CrewErrorCode;
import com.run_us.server.domains.crew.controller.model.enums.CrewException;
import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.run_us.server.global.common.GlobalConst.TIME_ZONE_ID;


@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "crew_join_requests")
@Entity
public class CrewJoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer crewId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CrewJoinRequestStatus status;

    @Column(name="answer", length = 100)
    private String answer;

    @Column(name = "requested_at", nullable = false)
    private ZonedDateTime requestedAt;

    @Column(name = "processed_at")
    private ZonedDateTime processedAt;

    @Column(name = "processed_by")
    private Integer processedBy;

    @Builder
    public CrewJoinRequest(
        Integer crewId, Integer userId,
        CrewJoinRequestStatus status,
        String answer,
        ZonedDateTime requestedAt,
        ZonedDateTime processedAt,
        Integer processedBy
    ){
        this.crewId = crewId;
        this.userId = userId;
        this.status = status;
        this.answer = answer;
        this.requestedAt = requestedAt;
        this.processedAt = processedAt;
        this.processedBy = processedBy;
    }

    public static CrewJoinRequest from(Integer userId, Integer crewId, String answer) {
        return CrewJoinRequest.builder()
                .crewId(crewId)
                .userId(userId)
                .answer(answer)
                .requestedAt(ZonedDateTime.now(ZoneId.of(TIME_ZONE_ID)))
                .status(CrewJoinRequestStatus.WAITING)
                .build();
    }

    private void approve(Integer processedUserId) {
        this.status = CrewJoinRequestStatus.APPROVED;
        this.processedAt = ZonedDateTime.now();
        this.processedBy = processedUserId;
    }

    private void reject(Integer processedByUserId) {
        this.status = CrewJoinRequestStatus.REJECTED;
        this.processedAt = ZonedDateTime.now();
        this.processedBy = processedByUserId;
    }

    public void review(Integer processedByUserId, CrewJoinRequestStatus status) {
        if(status == CrewJoinRequestStatus.APPROVED) {
            approve(processedByUserId);
        } else if(status == CrewJoinRequestStatus.REJECTED) {
            reject(processedByUserId);
        }
    }

    public void cancel() {
        if(this.status != CrewJoinRequestStatus.WAITING) {
            throw CrewException.of(CrewErrorCode.JOIN_REQUEST_ALREADY_PROCESSED);
        }
        this.status = CrewJoinRequestStatus.CANCELED;
        this.processedAt = ZonedDateTime.now();
    }
}
