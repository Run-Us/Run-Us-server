package com.run_us.server.domains.crew.domain;

import com.run_us.server.domains.crew.domain.enums.CrewJoinTypeEnum;
import com.run_us.server.domains.crew.domain.enums.CrewStatusEnum;
import com.run_us.server.domains.crew.domain.enums.CrewThemeTypeEnum;
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

    private String profileImage;

    @Column(length = 10)
    private String location;

    @Column(length = 300)
    private String intro;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CrewThemeTypeEnum themeType;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int memberCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CrewJoinTypeEnum joinType;

    @Column(name = "join_question", length = 50)
    private String joinQuestion;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CrewStatusEnum status = CrewStatusEnum.ACTIVE;

    @Builder
    public CrewDescription(
            String title,
            String profileImage,
            String location,
            String intro,
            CrewThemeTypeEnum themeType,
            User owner,
            CrewJoinTypeEnum joinType,
            String joinQuestion
    ){
        this.title = title;
        this.profileImage = profileImage;
        this.location = location;
        this.intro = intro;
        this.themeType = themeType;
        this.owner = owner;
        this.joinType = joinType;
        this.joinQuestion = joinQuestion;
    }
}
