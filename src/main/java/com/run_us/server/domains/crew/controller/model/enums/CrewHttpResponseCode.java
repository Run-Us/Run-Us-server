package com.run_us.server.domains.crew.controller.model.enums;

import com.run_us.server.global.exception.code.CustomResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CrewHttpResponseCode implements CustomResponseCode {
    CREW_CREATED("CSH2011", HttpStatus.CREATED, "크루 생성 성공", "크루 생성 성공"),
    CREW_HOME_FETCHED("CSH2002", HttpStatus.OK, "크루 홈 조회 성공", "크루 홈 조회 성공"),
    CREW_INFO_PAGE_FETCHED("CSH2003", HttpStatus.OK, "크루 정보 페이지 조회 성공", "크루 정보 페이지 조회 성공"),
    CREW_UPDATED("CSH2004", HttpStatus.OK, "크루 정보 수정 성공", "크루 정보 수정 성공"),
    CREW_JOIN_RULE_UPDATED("CSH2005", HttpStatus.OK, "크루 가입 방식 수정 성공", "크루 가입 방식 수정 성공"),
    CREW_SEARCH_SUCCESS("CSH2006", HttpStatus.OK, "크루 검색 성공", "크루 검색 성공"),
    CREW_CLOSE_SUCCESS("CSH2007", HttpStatus.OK, "크루 폐쇄 성공", "크루 폐쇄 성공"),

    JOIN_REQUEST_CREATED("CSH2021", HttpStatus.CREATED, "크루 가입 요청 성공", "크루 가입 요청 성공"),
    JOIN_REQUEST_CANCELLED("CSH2008", HttpStatus.OK, "크루 가입 요청 취소 성공", "크루 가입 요청 취소 성공"),
    JOIN_REQUEST_FETCHED("CSH2009", HttpStatus.OK, "크루 가입 요청 조회 성공", "크루 가입 요청 조회 성공"),
    JOIN_REQUEST_REVIEWED("CSH2010", HttpStatus.OK, "크루 가입 요청 처리 성공", "크루 가입 요청 처리 성공"),

    KICK_MEMBER_SUCCESS("CSH2031", HttpStatus.OK, "크루 멤버 추방 성공", "크루 멤버 추방 성공"),
    GET_MEMBERS_SUCCESS("CSH2032", HttpStatus.OK, "크루 멤버 리스트 조회 성공", "크루 멤버 리스트 조회 성공"),
    ;

    private final String code;
    private final HttpStatus httpStatusCode;
    private final String clientMessage;
    private final String logMessage;


    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getClientMessage() {
        return this.clientMessage;
    }

    @Override
    public String getLogMessage() {
        return this.logMessage;
    }

    @Override
    public HttpStatus getHttpStatusCode() {
        return this.httpStatusCode;
    }
}
