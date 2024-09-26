package com.run_us.server.domains.user.domain;

import com.run_us.server.global.common.DateAudit;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@SQLRestriction("deleted_at is null")
public class User extends DateAudit{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(name = "public_id", nullable = false)
  private String publicId;

  @Column(name = "nickname", nullable = false)
  private String nickname;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  @ColumnDefault("'NONE'")
  private Gender gender;

  @Column(name = "img_url")
  private String imgUrl;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  // 생성 관련 메소드

  /***
   * User 생성자
   * @param nickname 사용자 닉네임, 중복가능, Not null
   * @param birthDate 사용자 생년월일
   * @param gender 사용자 성별, default NONE
   * @param imgUrl 사용자 프로필 이미지 URL
   */
  @Builder
  public User(@NotNull String nickname, LocalDate birthDate, Gender gender, String imgUrl) {
    this.nickname = nickname;
    this.birthDate = birthDate;
    this.gender = gender;
    this.imgUrl = imgUrl;
  }

  @Override
  protected void prePersist() {
    super.prePersist();
    this.publicId = TSID.Factory.getTsid().toString();
  }

  // 비즈니스 로직 메소드

  /**
   * change profile image url method
   *
   * @param newImgUrl 변경할 이미지 URL
   * */
  public void changeProfileImgUrl(@NotNull String newImgUrl) {
    this.imgUrl = newImgUrl;
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
