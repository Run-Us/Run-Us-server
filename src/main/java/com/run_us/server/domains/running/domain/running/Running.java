package com.run_us.server.domains.running.domain.running;

import com.run_us.server.domains.running.domain.running.RunningConstraints.RunningConstraintsConverter;
import com.run_us.server.domains.running.domain.running.RunningDescription.RunningDescriptionConverter;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.global.common.CreationTimeAudit;
import com.run_us.server.global.util.PassCodeGenerator;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;

import lombok.*;
import org.locationtech.jts.geom.Point;

@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "runnings")
public class Running extends CreationTimeAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "public_id", nullable = false, columnDefinition = "CHAR(13)")
  private String publicKey;

  @Transient
  private String passcode;

  @Column(name = "start_location", nullable = false)
  private Point startLocation;

  @Column(name = "constraints", nullable = false)
  @Convert(converter = RunningConstraintsConverter.class)
  private RunningConstraints constraints;

  @Column(name = "annotation", nullable = false)
  @Convert(converter = RunningDescriptionConverter.class)
  private RunningDescription description;

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

  public List<Integer> getAllParticipantsId() {
    return this.participants.getAllParticipantsId();
  }

  @Override
  public void prePersist() {
    super.prePersist();
    this.publicKey = TSID.Factory.getTsid().toString();
    this.passcode = PassCodeGenerator.generatePassCode();
  }
}
