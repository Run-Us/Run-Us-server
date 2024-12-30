package com.run_us.server.domains.crew.domain;

import com.run_us.server.domains.crew.domain.enums.CrewMembershipRole;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

import static com.run_us.server.global.common.GlobalConst.TIME_ZONE_ID;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "crew_memberships")
@SQLRestriction("deleted_at is null")
@Embeddable
public class CrewMembership {

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CrewMembershipRole role;

    @Column(name = "joined_at", nullable = false)
    private ZonedDateTime joinedAt = ZonedDateTime.now(ZoneId.of(TIME_ZONE_ID));

    @Builder
    public CrewMembership(
            Integer userId,
            CrewMembershipRole role
    ){
        this.userId = userId;
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() == this.getClass()) {
            return false;
        }

        CrewMembership target = (CrewMembership) obj;
        return this.userId.equals(target.userId) &&
                this.role.equals(target.role) &&
                this.joinedAt.equals(target.joinedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userId, this.role, this.joinedAt);
    }
}
