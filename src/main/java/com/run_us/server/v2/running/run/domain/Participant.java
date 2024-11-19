package com.run_us.server.v2.running.run.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer userId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "run_id")
  private Run run;

  private boolean isAttended;

  public Participant(Integer userId, Run run) {
    this.userId = userId;
    this.run = run;
    this.isAttended = false;
  }
}
