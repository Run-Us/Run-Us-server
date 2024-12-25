package com.run_us.server.domains.crew.domain;

import com.run_us.server.domains.crew.domain.enums.CrewJoinType;
import com.run_us.server.domains.crew.domain.enums.CrewStatus;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.global.common.DateAudit;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "crew")
@SQLRestriction("deleted_at is null")
public class Crew extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "public_id", nullable = false, columnDefinition = "CHAR(13)")
    private String publicId;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private int memberCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CrewJoinType joinType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CrewStatus status;

    @Embedded
    private CrewDescription crewDescription;

    @ElementCollection
    @CollectionTable(name = "crew_join_requests", joinColumns = @JoinColumn(name="crew_id"))
    private List<CrewJoinRequest> joinRequests;

    @ElementCollection
    @CollectionTable(name = "crew_memberships", joinColumns = @JoinColumn(name="crew_id"))
    private List<CrewMembership> crewMemberships;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    @Override
    public void prePersist() {
        this.publicId = TSID.Factory.getTsid().toString();
        super.prePersist();
    }

    @Builder
    public Crew(
        User owner,
        CrewJoinType joinType,
        CrewDescription crewDescription,
        List<CrewMembership> crewMemberships
    ){
        this.owner = owner;
        this.joinType = joinType;
        this.crewDescription = crewDescription;
        this.memberCount = 1;
        this.status = CrewStatus.ACTIVE;
        this.crewMemberships = crewMemberships;
    }
}
