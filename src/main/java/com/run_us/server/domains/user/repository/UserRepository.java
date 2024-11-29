package com.run_us.server.domains.user.repository;

import com.run_us.server.domains.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Query(
      "SELECT u FROM User u JOIN u.profile p WHERE p.nickname = :nickname AND u.deletedAt IS NULL")
  Optional<User> findByNickname(String nickname);

  @Query("SELECT u FROM User u WHERE u.publicId = :publicId AND u.deletedAt IS NULL")
  Optional<User> findByPublicId(String publicId);

  @Query("SELECT u FROM User u WHERE u.id = :internalId")
  Optional<User> findByInternalId(Integer internalId);
}
