package com.run_us.server.domains.crew.domain;

import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import com.run_us.server.domains.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

import static com.run_us.server.global.common.GlobalConst.TIME_ZONE_ID;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "crew_join_requests")
@SQLRestriction("deleted_at is null")
@Embeddable
public class CrewJoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CrewJoinRequestStatus status = CrewJoinRequestStatus.WAITING;

    @Column(name="answer", length = 100)
    private String answer;

    @Column(name = "requested_at", nullable = false)
    private ZonedDateTime requestedAt = ZonedDateTime.now(ZoneId.of(TIME_ZONE_ID));

    @Column(name = "processed_at")
    private ZonedDateTime processedAt;

    @ManyToOne
    @JoinColumn(name = "processed_by")
    private User processedBy;

    @Builder
    public CrewJoinRequest(
            Integer userId,
            CrewJoinRequestStatus status,
            String answer,
            ZonedDateTime requestedAt,
            ZonedDateTime processedAt,
            User processedBy
    ){
        this.userId = userId;
        this.status = status;
        this.answer = answer;
        this.requestedAt = requestedAt;
        this.processedAt = processedAt;
        this.processedBy = processedBy;
    }

    public static CrewJoinRequest from(Integer userId, String answer) {
        return CrewJoinRequest.builder()
                .userId(userId)
                .answer(answer)
                .build();
    }

    public void approve(User processedBy) {
        this.status = CrewJoinRequestStatus.APPROVED;
        this.processedAt = ZonedDateTime.now();
        this.processedBy = processedBy;
    }

    public void reject(User processedBy) {
        this.status = CrewJoinRequestStatus.REJECTED;
        this.processedAt = ZonedDateTime.now();
        this.processedBy = processedBy;
    }

    public void cancel() {
        this.status = CrewJoinRequestStatus.CANCELED;
        this.processedAt = ZonedDateTime.now();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        CrewJoinRequest target = (CrewJoinRequest) obj;
        return this.userId.equals(target.userId) &&
                this.status.equals(target.status) &&
                this.requestedAt.equals(target.requestedAt) &&
                this.processedAt.equals(target.processedAt) &&
                this.processedBy.equals(target.processedBy);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userId, this.status, this.requestedAt, this.processedAt, this.processedBy);
    }
}
