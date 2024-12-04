package com.run_us.server.domains.crew.domain;

import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatusEnum;
import com.run_us.server.domains.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;

import java.time.ZonedDateTime;

@ToString
@Getter
@NoArgsConstructor
@Entity
@Table(name = "crew_join_requests")
@SQLRestriction("deleted_at is null")
public class CrewJoinRequest {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "crew_id", nullable = false)
    private Crew crew;

    @Column(nullable = false)
    private CrewJoinRequestStatusEnum status;

    @Column(name = "requested_at", nullable = false)
    private ZonedDateTime requestedAt;

    @Column(name = "processed_at")
    private ZonedDateTime processedAt;

    @ManyToOne
    @JoinColumn(name = "processed_by")
    private User processedBy;

}
