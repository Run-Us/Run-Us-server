package com.run_us.server.domains.crew.domain;

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
@Table(name = "crew_memberships")
@SQLRestriction("deleted_at is null")
public class CrewMembership {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="crew_id", nullable = false)
    private Crew crew;

    @Column(nullable = false)
    private CrewMembershipRoleEnum role;

    @Column(name = "joined_at", nullable = false)
    private ZonedDateTime joinedAt;

}
