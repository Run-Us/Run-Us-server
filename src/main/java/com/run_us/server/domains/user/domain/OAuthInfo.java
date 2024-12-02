package com.run_us.server.domains.user.domain;

import com.run_us.server.global.common.CreationTimeAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oauth_accounts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthInfo extends CreationTimeAudit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "INT")
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, columnDefinition = "INT")
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(name = "provider", nullable = false)
  private SocialProvider provider;

  @Column(name = "provider_user_id", nullable = false)
  private String providerId;

  /***
   * OAuthInfo Constructor
   * @param user OAuth연동할 유저 엔티티
   * @param provider 소셜 로그인 제공자
   * @param providerId 소셜 로그인 이메일 / guid
   */
  @Builder
  public OAuthInfo(
      @NotNull User user, @NotNull SocialProvider provider, @NotNull String providerId) {
    this.user = user;
    this.provider = provider;
    this.providerId = providerId;
  }
}
