package com.run_us.server.domains.user.domain;

import com.run_us.server.global.common.DateAudit;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@SQLRestriction("deleted_at is null")
@ToString
public class User extends DateAudit{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "public_id", nullable = false, columnDefinition = "CHAR(13)")
  private String publicId;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "id", referencedColumnName = "user_id")
  private Profile profile;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  // 생성 관련 메소드

  /***
   * User 생성자
   * @param profile 프로필 정보
   */
  @Builder
  private User(Profile profile) {
    this.profile = profile;
  }

  @Override
  protected void prePersist() {
    super.prePersist();
    this.publicId = TSID.Factory.getTsid().toString();
  }

  // 비즈니스 로직 메소드

  /**
   * change profile method
   *
   * @param profile 변경할 프로필 정보
   * */
  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  /***
   * soft delete method. deletedAt에 현재 시간을 저장
   */
  public void remove() {
    if(isRemoved()) {
      throw new IllegalStateException();
    }
    this.deletedAt = LocalDateTime.now();
  }

  private boolean isRemoved() {
    return deletedAt != null;
  }
}
