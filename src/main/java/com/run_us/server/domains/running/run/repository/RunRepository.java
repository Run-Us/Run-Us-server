package com.run_us.server.domains.running.run.repository;

import com.run_us.server.domains.running.run.domain.SessionAccessLevel;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.service.model.JoinedRunPreviewResponse;
import com.run_us.server.domains.running.run.service.model.GetRunPreviewResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RunRepository extends JpaRepository<Run, Integer> {
  Optional<Run> findByPublicId(String publicId);

  @Query(
      "SELECT new com.run_us.server.domains.running.run.service.model.GetRunPreviewResponse"
          + "(u.profile.nickname, u.profile.imgUrl, r.preview) "
          + "FROM Run r "
          + "join r.paceCategories "
          + "left join User u on r.hostId = u.id "
          + "WHERE r.id = :runId")
  GetRunPreviewResponse findByRunId(Integer runId);


  @Query(
      "SELECT new com.run_us.server.domains.running.run.service.model.JoinedRunPreviewResponse"
          + "(r.publicId, r.preview, COUNT(distinct p2.userId), u.publicId, pr.nickname, pr.imgUrl) "
          + "FROM Run r "
          + "join Participant p on r.id = p.run.id and p.userId = :userId "
          + "left join Participant p2 on r.id = p2.run.id "
          + "left join User u on r.hostId = u.id "
          + "left join Profile pr on u.id = pr.id "
          + "GROUP BY r.publicId, r.preview, pr.nickname, pr.imgUrl, u.publicId")
  Slice<JoinedRunPreviewResponse> findJoinedRunPreviews(Integer userId, PageRequest pageRequest);

  @Query(
      "SELECT r "
          + "FROM Run r "
          + "join fetch r.paceCategories "
          + "WHERE r.publicId IN :keys")
  List<Run> findAllByPublicId(List<String> keys);

  Slice<Run> findAllByCrewId(Integer crewId, PageRequest pageRequest);

  @Query(
      "SELECT r "
          + "FROM Run r "
          + "WHERE r.crewId = :crewId and r.preview.accessLevel = :accessLevel"
  )
  Slice<Run> findAllByCrewIdAndAccessLevel(Integer crewId, SessionAccessLevel accessLevel, PageRequest pageRequest);
}
