package com.run_us.server.domains.running.run.repository;

import com.run_us.server.domains.running.run.domain.Participant;
import com.run_us.server.domains.running.run.service.model.ParticipantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

  Optional<Participant> findByUserIdAndRunId(Integer userId, Integer runId);

  @Query(
      "SELECT new com.run_us.server.domains.running.run.service.model.ParticipantInfo("
          + "u.profile.nickname, u.profile.imgUrl, u.profile.totalDistance) "
          + "FROM Participant p "
          + "join User u on p.userId = u.id "
          + "WHERE p.run.id = :runId")
  List<ParticipantInfo> findByRunId(Integer runId);
}
