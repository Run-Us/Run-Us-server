package com.run_us.server.domains.crew.domain;

import com.run_us.server.domains.crew.domain.enums.CrewStatusEnum;
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

    @Column(nullable = false, length = 50)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    @Column(name = "deleted_at", nullable = false)
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private CrewStatusEnum status;

}
