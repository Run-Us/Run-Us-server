package com.run_us.server.domains.crew.controller.model.enums;

import com.run_us.server.global.exception.code.CustomResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CrewHttpResponseCode implements CustomResponseCode {
    CREW_CREATED("CSH2011", HttpStatus.CREATED, "크루 생성 성공", "크루 생성 성공"),
    CREW_HOME_INFO_FETCHED("CSH2002", HttpStatus.OK, "크루 홈 데이터 조회 성공", "크루 홈 데이터 조회 성공"),
    CREW_FETCHED("CSH2003", HttpStatus.OK, "크루 정보 조회 성공", "크루 정보 조회 성공"),
    CREW_UPDATED("CSH2004", HttpStatus.OK, "크루 정보 수정 성공", "크루 정보 수정 성공"),
    CREW_JOIN_RULE_UPDATED("CSH2005", HttpStatus.OK, "크루 가입 방식 수정 성공", "크루 가입 방식 수정 성공"),
    CREW_SEARCH_SUCCESS("CSH2006", HttpStatus.OK, "크루 검색 성공", "크루 검색 성공"),
    CREW_CLOSE_SUCCESS("CSH2007", HttpStatus.OK, "크루 폐쇄 성공", "크루 폐쇄 성공"),

    JOIN_REQUEST_CREATED("CSH2021", HttpStatus.CREATED, "크루 가입 요청 성공", "크루 가입 요청 성공"),

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
