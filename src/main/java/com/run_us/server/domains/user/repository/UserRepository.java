package com.run_us.server.domains.user.repository;

import com.run_us.server.domains.running.service.model.JoinedParticipant;
import com.run_us.server.domains.user.domain.Profile;
import com.run_us.server.domains.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

  @Query("SELECT u FROM User u JOIN u.profile p WHERE p.nickname = :nickname AND u.deletedAt IS NULL")
  Optional<User> findByNickname(String nickname);

  @Query("SELECT u FROM User u WHERE u.publicId = :publicId AND u.deletedAt IS NULL")
  Optional<User> findByPublicId(String publicId);

  @Query("SELECT new com.run_us.server.domains.running.service.model.JoinedParticipant(p.nickname, p.imgUrl)"
          + " FROM Profile p"
          + " WHERE p.userId IN :participantIds"
          + " AND p.userId IN (SELECT u.id FROM User u WHERE u.deletedAt IS NULL)")
  List<JoinedParticipant> findSimpleParticipantsByRunningId(List<Integer> participantIds);
}
