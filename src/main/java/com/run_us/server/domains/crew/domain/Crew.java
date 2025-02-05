package com.run_us.server.domains.crew.domain;

import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import com.run_us.server.domains.crew.domain.enums.CrewJoinType;
import com.run_us.server.domains.crew.domain.enums.CrewStatus;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.global.common.DateAudit;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @CollectionTable(name = "crew_memberships", joinColumns = @JoinColumn(name="crew_id"))
    private List<CrewMembership> crewMemberships = new ArrayList<>();

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public boolean isMember(Integer userId) {
        return crewMemberships.stream()
                .anyMatch(membership -> membership.getUserId().equals(userId));
    }

    public boolean isActive() {
        return this.status == CrewStatus.ACTIVE;
    }

    public void addMember(Integer userId) {
        this.crewMemberships.add(CrewMembership.builder()
            .userId(userId)
            .build()
        );
        this.memberCount++;
    }

    public void removeMember(Integer userId) {
        this.crewMemberships.removeIf(membership -> membership.getUserId().equals(userId));
        this.memberCount--;
    }

    public boolean isOwner(Integer userId) {
        return this.owner.getId().equals(userId);
    }

    public void updateCrewInfo(CrewDescription crewDescription) {
        this.crewDescription = crewDescription;
    }


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

    public void updateCrewJoinRule(CrewJoinType joinType, String joinQuestion) {
        this.joinType = joinType;
        this.getCrewDescription().updateJoinQuestion(joinQuestion);
    }
}
