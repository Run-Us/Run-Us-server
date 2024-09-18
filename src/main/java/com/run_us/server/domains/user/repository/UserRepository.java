package com.run_us.server.domain.user.repository;

import com.run_us.server.domain.user.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

  Optional<User> findByNickname(String nickname);
}
