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

import java.time.ZonedDateTime;
import java.util.Objects;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "crew_join_requests")
@SQLRestriction("deleted_at is null")
@Embeddable
public class CrewJoinRequest {

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

    public void approve(User processedBy) {
        this.status = CrewJoinRequestStatus.APPROVED;
        this.processedAt = ZonedDateTime.now();
        this.processedBy = processedBy;
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
