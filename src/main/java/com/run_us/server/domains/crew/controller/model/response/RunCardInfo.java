package com.run_us.server.domains.crew.controller.model.response;

import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.domain.RunPace;
import com.run_us.server.domains.running.run.domain.RunningPreview;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.global.common.GlobalConst;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class RunCardInfo {
    private String runningPublicId;
    private String topMessage;
    private String title;
    private String description;
    private String startAt;
    private List<RunPace> paceList;
    private Integer participantCount;
    private SimpleUserInfo createdBy;


    @Builder
    private RunCardInfo(
            String runningPublicId,
            String topMessage, String title, String description, String startAt,
            List<RunPace> paceList, Integer participantCount, SimpleUserInfo createdBy) {
        this.runningPublicId = runningPublicId;
        this.topMessage = topMessage;
        this.title = title;
        this.description = description;
        this.startAt = startAt;
        this.paceList = paceList;
        this.participantCount = participantCount;
        this.createdBy = createdBy;
    }

    public static RunCardInfo from(Run run, String topMessage, User createdUser) {
        RunningPreview preview = run.getPreview();

        return RunCardInfo.builder()
                .runningPublicId(run.getPublicId())
                .topMessage(topMessage)
                .title(preview.getTitle())
                .description(preview.getDescription())
                .startAt(preview.getBeginTime().format(DateTimeFormatter.ofPattern(GlobalConst.TIME_FORMAT_PATTERN)))
                .paceList(run.getPaceCategories())
                .createdBy(SimpleUserInfo.from(createdUser))
                .build();
    }

}