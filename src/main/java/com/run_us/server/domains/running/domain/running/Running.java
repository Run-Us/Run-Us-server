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

  @Column(name = "host_id")
  private Integer hostId;

  @Transient
  private String passcode;

  @Column(name = "start_location")
  private Point startLocation;

  @Column(name = "constraints")
  @Convert(converter = RunningConstraintsConverter.class)
  private RunningConstraints constraints;

  @Column(name = "annotation")
  @Convert(converter = RunningDescriptionConverter.class)
  private RunningDescription description;

  @Embedded
  private Participants participants = new Participants();

  // 같이 달리기 생성 메소드
  @Builder
  public Running(Integer hostId, Point startLocation, RunningConstraints constraints, RunningDescription description) {
    this.startLocation = startLocation;
    this.constraints = constraints;
    this.description = description;
  }

  // 혼자 달리기 생성 메소드
  public Running (Integer hostId) {
    this.hostId = hostId;
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
