package com.run_us.server.domain.user.model;

import static com.run_us.server.global.common.GlobalConsts.TIME_ZONE_ID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_penalties")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Penalty {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "desc")
  private String description;

  @Column(name = "penalty_type", nullable = false)
  private String penaltyType;

  @Column(name = "applied_at", nullable = false)
  private ZonedDateTime appliedAt;

  @Column(name = "expires_at", nullable = false)
  private ZonedDateTime expiresAt;

  @Builder
  public Penalty(String description, @NotNull String penaltyType, @NotNull ZonedDateTime expiresAt) {
    this.description = description;
    this.penaltyType = penaltyType;
    this.expiresAt = expiresAt;
  }

  @PrePersist
  public void onPersist() {
    this.appliedAt = ZonedDateTime.now(ZoneId.of(TIME_ZONE_ID));
  }

  public void applyPenaltyToUser(User user) {
    this.user = user;
  }
}
