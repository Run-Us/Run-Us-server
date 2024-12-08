package com.run_us.server.domains.crew.domain;

import com.run_us.server.global.common.DateAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@Entity
@Table(name = "crews")
@SQLRestriction("deleted_at is null")
public class Crew extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, columnDefinition = "CHAR(13)")
    private String publicId;

    @Embedded
    private CrewDescription crewDescription;

    @ElementCollection
    @CollectionTable(name = "crew_join_requests", joinColumns = @JoinColumn(name="crew_id"))
    private List<CrewJoinRequest> joinRequests;

    @ElementCollection
    @CollectionTable(name = "crew_memberships", joinColumns = @JoinColumn(name="crew_id"))
    private List<CrewMembership> crewMemberships;

    @Column(name = "deleted_at", nullable = false)
    private LocalDateTime deletedAt;

}
