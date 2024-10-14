package com.run_us.server.domains.running.domain.running;

import com.run_us.server.domains.running.domain.running.RunningConstraints.RunningConstraintsConverter;
import com.run_us.server.domains.running.domain.running.RunningDescription.RunningDescriptionConverter;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.global.common.DateAudit;
import com.run_us.server.global.util.PassCodeGenerator;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Running extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "public_key", nullable = false)
  private String publicKey;

  @Column(name = "passcode", nullable = false)
  private String passcode;

  @Column(name = "start_location", nullable = false)
  private Point startLocation;

  @Column(name = "running_constraints", nullable = false)
  @Convert(converter = RunningConstraintsConverter.class)
  private RunningConstraints constraints;

  @Column(name = "running_annotation", nullable = false)
  @Convert(converter = RunningDescriptionConverter.class)
  private RunningDescription description;

  @Column(name = "is_verified", nullable = false)
  private boolean isVerified = false;

  @Embedded
  private Participants participants = new Participants();

  // 생성 메소드
  @Builder
  public Running(Point startLocation, RunningConstraints constraints, RunningDescription description) {
    this.startLocation = startLocation;
    this.constraints = constraints;
    this.description = description;
  }

  // 비즈니스 로직
  public void addParticipant(User user) {
    this.participants.add(user.getId());
  }

  public boolean hasParticipant(User user) {
    return this.participants.contains(user.getId());
  }

  public void removeParticipant(User user) {
    this.participants.remove(user.getId());
  }

  public List<Long> getAllParticipantsId() {
    return this.participants.getAllParticipantsId();
  }

  public void verify() {
    this.isVerified = true;
  }

  @Override
  public void prePersist() {
    super.prePersist();
    this.publicKey = TSID.Factory.getTsid().toString();
    this.passcode = PassCodeGenerator.generatePassCode();
  }
}
