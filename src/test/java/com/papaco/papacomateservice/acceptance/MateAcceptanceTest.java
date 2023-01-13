package com.papaco.papacomateservice.acceptance;

import com.papaco.papacomateservice.mate.application.port.output.ReviewerRepository;
import com.papaco.papacomateservice.mate.domain.entity.Reviewer;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.papaco.papacomateservice.acceptance.MateSteps.*;

class MateAcceptanceTest extends AcceptanceTest {
    @Autowired
    private ReviewerRepository reviewerRepository;
    private UUID 프로젝트 = UUID.fromString("6fec2b25-9ad4-4dba-9f54-bc3c3305a6a5");
    private Long 리뷰어 = 1L;

    @BeforeEach
    public void setUp() {
        super.setUp();
        reviewerRepository.save(new Reviewer(리뷰어, true));
    }

    /**
     * Feature: 메이트 관리
     *
     *   Scenario: 메이트를 관리한다.
     *     When 메이트 제안 요청
     *     Then 메이트 제안 요청됨
     *     When 메이트 제안 수락 요청
     *     Then 메이트 연결됨
     *     When 메이트 종료 요청
     *     Then 메이트 종료됨
     */
    @DisplayName("메이트 매칭을 진행한다")
    @Test
    void matching() {
        ExtractableResponse<Response> proposeResponse = 메이트_제안_요청(프로젝트, 리뷰어);
        메이트_제안_요청됨(proposeResponse);

        ExtractableResponse<Response> joinResponse = 메이트_제안_수락_요청(proposeResponse);
        메이트_연결됨(joinResponse);

        ExtractableResponse<Response> finishResponse = 메이트_종료_요청(proposeResponse);
        메이트_종료됨(finishResponse);
    }

    /**
     * Feature: 메이트 제안 취소
     *
     *   Scenario: 메이트 제안을 취소한다.
     *     When 메이트 제안 요청
     *     Then 메이트 제안 요청됨
     *     When 메이트 제안 삭제 요청
     *     Then 메이트 제안 삭제됨
     */
    @DisplayName("메이트 제안을 삭제한다")
    @Test
    void remove() {
        ExtractableResponse<Response> proposeResponse = 메이트_제안_요청(프로젝트, 리뷰어);
        메이트_제안_요청됨(proposeResponse);

        ExtractableResponse<Response> removeResponse = 메이트_제안_삭제_요청(proposeResponse);
        메이트_제안_삭제됨(removeResponse);
    }

    /**
     * Feature: 메이트 제안 거절
     *
     *   Scenario: 메이트 제안을 거절한다.
     *     When 메이트 제안 요청
     *     Then 메이트 제안 요청됨
     *     When 메이트 제안 거절 요청
     *     Then 메이트 제안 거절됨
     */
    @DisplayName("메이트 제안을 거절한다")
    @Test
    void reject() {

    }
}
