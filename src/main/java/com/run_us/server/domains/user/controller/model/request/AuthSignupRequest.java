package com.run_us.server.domains.user.controller.model.request;

import com.run_us.server.domains.user.domain.Gender;
import com.run_us.server.domains.user.domain.Profile;
import com.run_us.server.domains.user.domain.SocialProvider;
import com.run_us.server.global.validator.annotation.EnumValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class AuthSignupRequest {
    //OAuth region
    @NotBlank
    private String oidcToken;

    @NotBlank
    @EnumValid(enumClass = SocialProvider.class)
    private String provider;

    //profile region
    @NotBlank
    @Pattern(
            regexp = "^[가-힣a-zA-Z0-9]{2,8}$",
            message = "닉네임은 2-8자의 한글, 영문, 숫자만 사용 가능합니다"
    )
    private String nickname;

    @URL
    private String imgUrl;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent
    private LocalDate birthdate;

    @EnumValid(enumClass = Gender.class)
    private String gender;

    @Positive
    private Integer pace;

    public Profile toProfile() {
        return Profile.builder()
                .nickname(nickname)
                .imgUrl(imgUrl)
                .birthDate(birthdate)
                .gender(Optional.ofNullable(gender).map(Gender::valueOf).orElse(null))
                .pace(pace)
                .build();
    }
}
