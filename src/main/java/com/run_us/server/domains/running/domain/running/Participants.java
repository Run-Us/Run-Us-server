package com.run_us.server.domains.running.domain.running;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participants implements Serializable {

  @ElementCollection
  @CollectionTable(
      name = "running_participants",
      joinColumns = @JoinColumn(name = "running_id")
  )
  @Column(name = "user_id")
  private Set<Integer> participants = new HashSet<>();

  public void add(Integer participantId) {
    this.participants.add(participantId);
  }

  public void remove(Integer participantId) {
    this.participants.remove(participantId);
  }

  public boolean contains(Integer participantId) {
    return this.participants.contains(participantId);
  }

  /***
   * 참가자 목록을 반환합니다.
   * @return 참가자 목록
   */
  public List<Integer> getAllParticipantsId() {
    return List.copyOf(participants);
  }
}
