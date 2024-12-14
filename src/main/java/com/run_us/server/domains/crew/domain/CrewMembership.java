package com.run_us.server.domains.crew.domain;

import com.run_us.server.domains.crew.domain.enums.CrewMembershipRole;
import com.run_us.server.domains.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.run_us.server.global.common.GlobalConst.TIME_ZONE_ID;

@ToString
@Getter
@NoArgsConstructor
@Table(name = "crew_memberships")
@SQLRestriction("deleted_at is null")
@Embeddable
public class CrewMembership {

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CrewMembershipRole role;

    @Column(name = "joined_at", nullable = false)
    private ZonedDateTime joinedAt;

    @PrePersist
    public void prePersist() {
        this.joinedAt = ZonedDateTime.now(ZoneId.of(TIME_ZONE_ID));
    }

    @Builder
    public CrewMembership(
            User user,
            CrewMembershipRole role
    ){
        this.user = user;
        this.role = role;
    }
}
