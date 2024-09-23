package com.run_us.server.domains.running.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
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
      name = "participants",
      joinColumns = @JoinColumn(name = "running_id")
  )
  private Set<Long> participants = new HashSet<>();

  public void add(Long participantId) {
    this.participants.add(participantId);
  }

  public void remove(Long participantId) {
    this.participants.remove(participantId);
  }

  public boolean contains(Long participantId) {
    return this.participants.contains(participantId);
  }

  /***
   * 참가자 목록을 반환합니다.
   * @return 참가자 목록
   */
  public List<Long> getAllParticipantsId() {
    return List.copyOf(participants);
  }
}
