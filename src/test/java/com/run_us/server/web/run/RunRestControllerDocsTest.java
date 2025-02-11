package com.run_us.server.web.run;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.run_us.server.annotations.MockUser;
import com.run_us.server.annotations.RestDocsTest;
import com.run_us.server.config.*;
import com.run_us.server.domains.running.ParticipantFixtures;
import com.run_us.server.domains.running.RunFixtures;
import com.run_us.server.domains.running.run.controller.RunController;
import com.run_us.server.domains.running.run.controller.model.RunningHttpResponseCode;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.service.model.CustomRunCreateResponse;
import com.run_us.server.domains.running.run.service.model.FetchRunningIdResponse;
import com.run_us.server.domains.running.run.service.model.JoinedRunPreviewResponse;
import com.run_us.server.domains.running.run.service.model.SessionRunCreateResponse;
import com.run_us.server.domains.running.run.service.usecase.*;
import com.run_us.server.global.common.SuccessResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static com.run_us.server.config.RestDocsUtil.getDocumentRequest;
import static com.run_us.server.config.RestDocsUtil.getDocumentResponse;

@DisplayName("Run API 문서화")
@RestDocsTest
@MockUser
public class RunRestControllerDocsTest {

  @Mock
  private RunCreateUseCase runCreateUseCase;
  @Mock
  private RunRegisterUseCase runRegisterUseCase;
  @Mock
  private RunDeleteUseCase runDeleteUseCase;
  @Mock
  private RunPreviewUseCase runPreviewUseCase;
  @InjectMocks
  private RunController runController;

  @DisplayName("커스텀런 생성")
  @Test
  void 커스텀런_생성(RestDocumentationContextProvider contextProvider) throws Exception {

    Run run = RunFixtures.createRun();
    ReflectionTestUtils.setField(run, "publicId", "1234");

    Mockito.when(runCreateUseCase.saveNewCustomRun(any()))
        .thenReturn(SuccessResponse.of(RunningHttpResponseCode.CUSTOM_RUN_CREATED, CustomRunCreateResponse.from(run, "1234")));

    FieldDescriptor[] fieldDescriptors = new FieldDescriptor[]{
        fieldWithPath("code").type(String.class).description("응답코드(정상:)"),
        fieldWithPath("message").type(String.class).description("응답 메세지"),
        fieldWithPath("success").type(String.class).description("성공 여부"),
        fieldWithPath("payload.runningPublicId").description(String.class).description("생성한 러닝ID"),
        fieldWithPath("payload.passcode").type(String.class).description("참가코드")
    };

    ResultActions actions = MockMvcFactory.getRestDocsMockMvc(contextProvider, TestConst.LOCAL, runController).perform(RestDocumentationRequestBuilders
        .post("/runnings").param("mode", "custom")
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("publicUserId", "123456")
        .content(""))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(MockMvcRestDocumentation.document("POST-create-custom-run",
            getDocumentRequest(),
            getDocumentResponse(),
            responseFields(
                fieldDescriptors
            )
        ))
        .andDo(MockMvcRestDocumentationWrapper.document("POST-create-custom-run",
            getDocumentRequest(),
            getDocumentResponse(),
            resource(ResourceSnippetParameters.builder()
                .responseFields(
                    fieldDescriptors
                ).build())));

  }

  @DisplayName("노멀런_생성")
  @Test
  void 노멀런_생성(RestDocumentationContextProvider contextProvider) throws Exception {

    Run run = RunFixtures.createRun();

    ReflectionTestUtils.setField(run, "publicId", "1234");

    Mockito.when(runCreateUseCase.saveNewSessionRun(any(), any()))
        .thenReturn(SuccessResponse.of(RunningHttpResponseCode.SESSION_RUN_CREATED, SessionRunCreateResponse.from(run)));

    String createRunJson = """
        {
        	"title": "우리 러닝해요",
        	"description": "러닝하자러닝",
        	"startDateTime": "2025-01-20T16:04+09:00",
        	"meetingPlace": "서울턱별시",
        	"accessLevel": "ALLOW_ALL",
        	"paceCategories": ["PACE_500"]
        }
        """;

    FieldDescriptor[] requestFields = new FieldDescriptor[]{
        fieldWithPath("title").type(String.class).description(""),
        fieldWithPath("description").type(String.class).description(""),
        fieldWithPath("startDateTime").type(String.class).description(""),
        fieldWithPath("meetingPlace").type(String.class).description(""),
        fieldWithPath("accessLevel").type(String.class).description(""),
        fieldWithPath("paceCategories").type(List.class).description(""),
        fieldWithPath("crewPublicId").type(String.class).optional().description(""),
    };

    FieldDescriptor[] responseFields = new FieldDescriptor[]{
        fieldWithPath("code").type(String.class).description("응답코드(정상:)"),
        fieldWithPath("message").type(String.class).description("응답 메세지"),
        fieldWithPath("success").type(String.class).description("성공 여부"),
        fieldWithPath("payload.runningPublicId").description(String.class).description("생성한 러닝ID"),
        fieldWithPath("payload.runningPreview.title").type(String.class).description("제목"),
        fieldWithPath("payload.runningPreview.description").type(String.class).description("설명"),
        fieldWithPath("payload.runningPreview.beginTime").type(String.class).description("시작시간"),
        fieldWithPath("payload.runningPreview.accessLevel").type(String.class).description("노출범위"),
        fieldWithPath("payload.runningPreview.meetingPoint").type(String.class).description("만나느장소")
    };

    ResultActions actions = MockMvcFactory.getRestDocsMockMvc(contextProvider, TestConst.LOCAL, runController).perform(RestDocumentationRequestBuilders
            .post("/runnings").param("mode", "normal")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createRunJson)
            .requestAttr("publicUserId", "123456")
            )
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(MockMvcRestDocumentation.document("POST-create-session-run",
            getDocumentRequest(),
            getDocumentResponse(),
            requestFields(requestFields),
            responseFields(
                responseFields
            )
        ))
        .andDo(MockMvcRestDocumentationWrapper.document("POST-create-session-run",
            getDocumentRequest(),
            getDocumentResponse(),
            resource(ResourceSnippetParameters.builder()
                .requestFields(requestFields)
                .responseFields(
                    responseFields
                ).build())));

  }

  @DisplayName("러닝 참가 신청")
  @Test
  void 러닝_참가신청(RestDocumentationContextProvider contextProvider) throws Exception {
    Mockito.when(runRegisterUseCase.registerRun(any(),any()))
        .thenReturn(SuccessResponse.of(RunningHttpResponseCode.PARTICIPANT_REGISTERED, ParticipantFixtures.createParticipantInfos()));

    FieldDescriptor[] responseFields = new FieldDescriptor[]{
        fieldWithPath("code").type(String.class).description("응답코드(정상:)"),
        fieldWithPath("message").type(String.class).description("응답 메세지"),
        fieldWithPath("success").type(String.class).description("성공 여부"),
        fieldWithPath("payload[].name").type(String.class).description("참여자 이름").optional(),
        fieldWithPath("payload[].imgUrl").type(String.class).description("이미지 url").optional(),
        fieldWithPath("payload[].totalDistanceInMeters").type(String.class).description("총 달린 거리").optional()
    };

    ResultActions actions = MockMvcFactory.getRestDocsMockMvc(contextProvider, TestConst.LOCAL, runController).perform(RestDocumentationRequestBuilders
            .post("/runnings/{runningId}/registration", "123")
            .contentType(MediaType.APPLICATION_JSON)
            .param("runningId", "1123123")
            .requestAttr("publicUserId", "123456")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcRestDocumentation.document("POST-register-session-run",
            getDocumentRequest(),
            getDocumentResponse(),
            responseFields(
                responseFields
            )
        ))
        .andDo(MockMvcRestDocumentationWrapper.document("POST-register-session-run",
            getDocumentRequest(),
            getDocumentResponse(),
            resource(ResourceSnippetParameters.builder()
                .responseFields(
                    responseFields
                ).build())));
  }

  @DisplayName("러닝 참여자 조회")
  @Test
  void 러닝_참여자_조회(RestDocumentationContextProvider contextProvider) throws Exception {
    Mockito.when(runRegisterUseCase.getRunParticipants(any()))
        .thenReturn(SuccessResponse.of(RunningHttpResponseCode.PARTICIPANTS_FETCHED, ParticipantFixtures.createParticipantInfos()));

    FieldDescriptor[] responseFields = new FieldDescriptor[]{
        fieldWithPath("code").type(String.class).description("응답코드(정상:)"),
        fieldWithPath("message").type(String.class).description("응답 메세지"),
        fieldWithPath("success").type(String.class).description("성공 여부"),
        fieldWithPath("payload[].name").type(String.class).description("참여자 이름").optional(),
        fieldWithPath("payload[].imgUrl").type(String.class).description("이미지 url").optional(),
        fieldWithPath("payload[].totalDistanceInMeters").type(String.class).description("총 달린 거리").optional()
    };

    ResultActions actions = MockMvcFactory.getRestDocsMockMvc(contextProvider, TestConst.LOCAL, runController).perform(RestDocumentationRequestBuilders
            .get("/runnings/{runningId}/participants", "123")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcRestDocumentation.document("GET-fetch-run-participants",
            getDocumentRequest(),
            getDocumentResponse(),
            responseFields(
                responseFields
            )
        ))
        .andDo(MockMvcRestDocumentationWrapper.document("GET-fetch-run-participants",
            getDocumentRequest(),
            getDocumentResponse(),
            resource(ResourceSnippetParameters.builder()
                .responseFields(
                    responseFields
                ).build())));
  }

  @DisplayName("러닝 참가 코드 조회")
  @Test
  void 러닝_참가코드_조회(RestDocumentationContextProvider contextProvider) throws Exception {
    Mockito.when(runRegisterUseCase.getRunningIdWithPasscode(any()))
        .thenReturn(SuccessResponse.of(RunningHttpResponseCode.ROOM_ID_FETCHED, new FetchRunningIdResponse("123", "1Q2R")));

    FieldDescriptor[] responseFields = new FieldDescriptor[]{
        fieldWithPath("code").type(String.class).description("응답코드(정상:)"),
        fieldWithPath("message").type(String.class).description("응답 메세지"),
        fieldWithPath("success").type(String.class).description("성공 여부"),
        fieldWithPath("payload.runningPublicId").type(String.class).description("패스코드에 해당하는 러닝 id"),
        fieldWithPath("payload.passcode").type(String.class).description("패스코드")


    };

    ResultActions actions = MockMvcFactory.getRestDocsMockMvc(contextProvider, TestConst.LOCAL, runController).perform(RestDocumentationRequestBuilders
            .get("/runnings/id")
            .param("passcode", "1Q2R")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcRestDocumentation.document("GET-fetch-runningID-with-passcode",
            getDocumentRequest(),
            getDocumentResponse(),
            responseFields(
                responseFields
            )
        ))
        .andDo(MockMvcRestDocumentationWrapper.document("GET-fetch-runningID-with-passcode",
            getDocumentRequest(),
            getDocumentResponse(),
            resource(ResourceSnippetParameters.builder()
                .responseFields(
                    responseFields
                ).build())));
  }

  @DisplayName("러닝 삭제")
  @Test
  void 러닝_삭제(RestDocumentationContextProvider contextProvider) throws Exception {

    FieldDescriptor[] responseFields = new FieldDescriptor[]{
        fieldWithPath("code").type(String.class).description("응답코드(정상:)"),
        fieldWithPath("message").type(String.class).description("응답 메세지"),
        fieldWithPath("success").type(String.class).description("성공 여부"),
        fieldWithPath("payload").type(String.class).optional().description(""),
    };

    ResultActions actions = MockMvcFactory.getRestDocsMockMvc(contextProvider, TestConst.LOCAL, runController).perform(RestDocumentationRequestBuilders
            .delete("/runnings/{runningId}", "1234")
            .requestAttr("publicUserId", "123456")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcRestDocumentation.document("DELETE-running",
            getDocumentRequest(),
            getDocumentResponse(),
            responseFields(
                responseFields
            )
        ))
        .andDo(MockMvcRestDocumentationWrapper.document("DELETE-running",
            getDocumentRequest(),
            getDocumentResponse(),
            resource(ResourceSnippetParameters.builder()
                .responseFields(
                    responseFields
                ).build())));
  }

  @DisplayName("참가 예정인 러닝 조회")
  @Test
  void 참가_예정_러닝_조회(RestDocumentationContextProvider contextProvider) throws Exception {

    List<JoinedRunPreviewResponse> joinedRunPreviewResponses = List.of(
        new JoinedRunPreviewResponse(
            "1234",
            RunFixtures.createRunningPreview(),
            10L,
            "129312",
            "하하하",
            "default"
        ),
        new JoinedRunPreviewResponse(
            "1235",
            RunFixtures.createRunningPreview(),
            12L,
            "129312",
            "재미있는 꿀잼런",
            "default"
        )
    );

    Mockito.when(runPreviewUseCase.getJoinedRunPreview(any(), any(), any()))
        .thenReturn(SuccessResponse.of(RunningHttpResponseCode.RUN_PREVIEW_FETCHED, joinedRunPreviewResponses));

    FieldDescriptor[] responseFields = new FieldDescriptor[]{
        fieldWithPath("code").type(String.class).description("응답코드(정상:)"),
        fieldWithPath("message").type(String.class).description("응답 메세지"),
        fieldWithPath("success").type(String.class).description("성공 여부"),
        fieldWithPath("payload[].runningPublicId").type(String.class).optional().description("러닝 Id"),
        fieldWithPath("payload[].title").type(String.class).description("제목"),
        fieldWithPath("payload[].description").type(String.class).description("설명"),
        fieldWithPath("payload[].startAt").type(String.class).optional().description("시작 시간"),
        fieldWithPath("payload[].runPaces").type(List.class).optional().description("페이스 카테고리"),
        fieldWithPath("payload[].participantCount").type(String.class).optional().description("참여자 수"),
        //주최자 정보
        fieldWithPath("payload[].createdBy.userPublicId").type(String.class).optional().description("주최자 Id"),
        fieldWithPath("payload[].createdBy.name").type(String.class).optional().description("머릿말"),
        fieldWithPath("payload[].createdBy.imgUrl").type(String.class).optional().description("이미지 주소"),
    };

    ResultActions actions = MockMvcFactory.getRestDocsMockMvc(contextProvider, TestConst.LOCAL, runController).perform(RestDocumentationRequestBuilders
            .get("/runnings/registrations")
            .param("runningPage", "0")
            .param("runningSize", "10")
            .requestAttr("publicUserId", "123456")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcRestDocumentation.document("GET-registered-running",
            getDocumentRequest(),
            getDocumentResponse(),
            responseFields(
                responseFields
            )
        ))
        .andDo(MockMvcRestDocumentationWrapper.document("GET-registered-running",
            getDocumentRequest(),
            getDocumentResponse(),
            resource(ResourceSnippetParameters.builder()
                .responseFields(
                    responseFields
                ).build())));
  }

  @DisplayName("참가 취소")
  @Test
  void 러닝_참가_취소(RestDocumentationContextProvider contextProvider) throws Exception {

    Mockito.when(runRegisterUseCase.cancelRun(any(), any()))
        .thenReturn(SuccessResponse.messageOnly(RunningHttpResponseCode.RUN_PREVIEW_FETCHED));

    FieldDescriptor[] responseFields = new FieldDescriptor[]{
        fieldWithPath("code").type(String.class).description("응답코드(정상:)"),
        fieldWithPath("message").type(String.class).description("응답 메세지"),
        fieldWithPath("success").type(String.class).description("성공 여부"),
        fieldWithPath("payload").type(String.class).optional().description(""),
    };

    ResultActions actions = MockMvcFactory.getRestDocsMockMvc(contextProvider, TestConst.LOCAL, runController).perform(RestDocumentationRequestBuilders
            .delete("/runnings/{runningId}/registration", "412321312")
            .requestAttr("publicUserId", "123456")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcRestDocumentation.document("DELETE-registered-running",
            getDocumentRequest(),
            getDocumentResponse(),
            responseFields(
                responseFields
            )
        ))
        .andDo(MockMvcRestDocumentationWrapper.document("DELETE-registered-running",
            getDocumentRequest(),
            getDocumentResponse(),
            resource(ResourceSnippetParameters.builder()
                .responseFields(
                    responseFields
                ).build())));
  }
}
