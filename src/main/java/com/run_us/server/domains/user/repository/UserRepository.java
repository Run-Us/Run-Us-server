package com.run_us.server.domains.user.repository;

import com.run_us.server.domains.running.controller.JoinedParticipantsDto;
import com.run_us.server.domains.user.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

  Optional<User> findByNickname(String nickname);

  @Query("SELECT u FROM User u WHERE u.publicId = :publicId AND u.deletedAt IS NULL")
  Optional<User> findByPublicId(String publicId);

  @Query("SELECT new com.run_us.server.domains.running.controller.JoinedParticipantsDto(u.nickname, u.imgUrl)"
      + " FROM User u WHERE u.id IN :participantIds")
  List<JoinedParticipantsDto> findSimpleParticipantsByRunningId(List<Long> participantIds);
}
