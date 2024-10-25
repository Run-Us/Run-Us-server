package com.run_us.server.domains.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile")
@Getter
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "pace")
    private Integer pace;

    @Column(name = "total_distance", nullable = false)
    private Integer totalDistance = 0;

    @Column(name = "total_time", nullable = false)
    private Integer totalTime = 0;

    @Column(name = "longest_distance", nullable = false)
    private Integer longestDistance = 0;

    @Column(name = "longest_time", nullable = false)
    private Integer longestTime = 0;

    @Builder
    public Profile(User user, String nickname, String imgUrl, LocalDate birthDate,
                   Gender gender, Integer pace) {
        this.user = user;
        this.nickname = nickname;
        this.imgUrl = imgUrl;
        this.birthDate = birthDate;
        this.gender = gender;
        this.pace = pace;
    }
}