package com.run_us.server.domains.crew.domain;

import com.run_us.server.domains.crew.domain.enums.CrewJoinType;
import com.run_us.server.domains.crew.domain.enums.CrewStatus;
import com.run_us.server.domains.crew.domain.enums.CrewThemeType;
import com.run_us.server.domains.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

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

    // TODO : 크루 성격도 상태로 보아야 할까?
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
}
