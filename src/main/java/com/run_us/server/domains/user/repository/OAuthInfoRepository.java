package com.run_us.server.domains.user.repository;

import com.run_us.server.domains.user.domain.OAuthInfo;
import com.run_us.server.domains.user.domain.SocialProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthInfoRepository extends JpaRepository<OAuthInfo, Long> {

  @Query("SELECT o FROM OAuthInfo o WHERE o.provider = :provider AND o.providerId = :providerId")
  Optional<OAuthInfo> findByProviderAndProviderId(SocialProvider provider, String providerId);
}
