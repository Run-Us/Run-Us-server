package com.run_us.server.domains.crew.domain;

import com.run_us.server.domains.crew.domain.enums.CrewThemeType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@ToString
@Getter
@NoArgsConstructor
@Embeddable
public class CrewDescription {

    @Column(nullable = false, length = 10)
    private String title;

    private String profileImageUrl;

    @Column(length = 10)
    private String location;

    @Column(length = 300)
    private String intro;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CrewThemeType themeType;

    @Column(name = "join_question", length = 50)
    private String joinQuestion;

    @Builder
    public CrewDescription(
            String title,
            String profileImageUrl,
            String location,
            String intro,
            CrewThemeType themeType,
            String joinQuestion
    ){
        this.title = title;
        this.profileImageUrl = profileImageUrl;
        this.location = location;
        this.intro = intro;
        this.themeType = themeType;
        this.joinQuestion = joinQuestion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        CrewDescription target = (CrewDescription) obj;

        return this.title.equals(target.title) &&
                this.profileImageUrl.equals(target.profileImageUrl) &&
                this.location.equals(target.location) &&
                this.intro.equals(target.intro) &&
                this.themeType == target.themeType &&
                this.joinQuestion.equals(target.joinQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.profileImageUrl, this.location, this.intro, this.themeType, this.joinQuestion);
    }
}
