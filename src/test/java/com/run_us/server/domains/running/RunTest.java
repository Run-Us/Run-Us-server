package com.run_us.server.domains.running;

import com.run_us.server.domains.running.run.domain.SessionAccessLevel;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.domain.RunStatus;
import com.run_us.server.domains.running.run.domain.RunningPreview;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.ZonedDateTime;

import static com.run_us.server.domains.running.common.RunningConst.MAX_LIVE_SESSION_CREATION_TIME;
import static org.junit.jupiter.api.Assertions.*;

class RunTest {

  @DisplayName("생성 테스트")
  @Test
  void test_create_run() {
    Run run = RunFixtures.createRun();

    assertNotNull(run);
  }

  @DisplayName("러닝 종료 상태 전환 테스트")
  @Test
  void test_change_run_status_to_end() {
    Run run = RunFixtures.createRun();
    run.changeStatus(RunStatus.FINISHED);
    assertEquals(run.getStatus(), RunStatus.FINISHED);
  }

  @DisplayName("러닝 세션 정보 수정")
  @Test
  void test_modify_run_session_info() {
    Run run = RunFixtures.createRun();
    ZonedDateTime now = ZonedDateTime.now();
    RunningPreview runningPreview = RunningPreview.builder()
        .title("제목")
        .meetingPoint("평화의문")
        .accessLevel(SessionAccessLevel.ALLOW_ALL)
        .beginTime(now)
        .build();

    run.modifySessionInfo(runningPreview);

    RunningPreview preview = run.getPreview();
    Assertions.assertThat(preview.getTitle()).isEqualTo("제목");
    Assertions.assertThat(preview.getMeetingPoint()).isEqualTo("평화의문");
  }

  @DisplayName("러닝 상태에 따른 삭제 가능 여부 테스트")
  @ParameterizedTest
  @EnumSource(
      value = RunStatus.class,
      names = {"WAITING", "CANCELLED"},
      mode = EnumSource.Mode.EXCLUDE)
  void test_deletable_except_WAITING_status(RunStatus runStatus) {
    Run run = RunFixtures.createRun();
    run.changeStatus(runStatus);
    assertFalse(run.isDeletable());
  }

  @DisplayName("라이브 세션 생성 시 생성자의 id로 세션 호스트 수정")
  @Test
  void test_change_live_session_host() {
    Run run = RunFixtures.createRun();
    Integer liveSessionCreatorId = 2;

    //when
    run.openLiveSession(liveSessionCreatorId);

    //then
    assertEquals(liveSessionCreatorId, run.getSessionHostId());
  }

  @DisplayName("세션 시작 시간 10분 초과하면 실시간 러닝을 시작할 수 없음")
  @Test
  void test_should_return_true_when_current_time_is_behind_beginning_time(){
    Run run = RunFixtures.createRun();
    ZonedDateTime beginsAt = ZonedDateTime.now().minusMinutes(MAX_LIVE_SESSION_CREATION_TIME + 1);
    run.modifySessionInfo(RunningPreview.builder().beginTime(beginsAt).build());

    //when
    boolean isBeginningTimePassed = run.isCreationTimeOver();

    //then
    assertTrue(isBeginningTimePassed);
  }
}
