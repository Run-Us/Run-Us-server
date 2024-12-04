package com.run_us.server.domains.crew.domain;

import com.run_us.server.domains.user.domain.User;
import com.run_us.server.global.common.DateAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

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

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private CrewStatusEnum status;

}
