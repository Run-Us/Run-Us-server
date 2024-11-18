package com.run_us.server.v2.running.run.repository;

import com.run_us.server.v2.running.run.domain.Run;
import com.run_us.server.v2.running.run.service.model.GetRunPreviewResponse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RunRepository extends JpaRepository<Run, Integer> {
  Optional<Run> findByPublicId(String publicId);

  @Query(
      "SELECT new com.run_us.server.v2.running.run.service.model.GetRunPreviewResponse"
          + "(u.profile.nickname, u.profile.imgUrl, r.preview) "
          + "FROM Run r "
          + "left join User u on r.hostId = u.id "
          + "WHERE r.id = :runId")
  GetRunPreviewResponse findByRunId(Integer runId);
}
