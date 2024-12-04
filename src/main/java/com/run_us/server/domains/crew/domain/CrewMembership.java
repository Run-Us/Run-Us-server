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
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="crew_id")
    private Crew crew;

    private CrewMembershipRoleEnum role;

    @Column(name = "join_at")
    private ZonedDateTime joinedAt;

}
