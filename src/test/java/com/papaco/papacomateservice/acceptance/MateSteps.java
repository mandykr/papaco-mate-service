package com.papaco.papacomateservice.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MateSteps {
    private static final String ENDPOINT = "/mates";

    public static ExtractableResponse<Response> 메이트_제안_요청(UUID projectId, Long reviewerId) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post(getURI(projectId, reviewerId, "propose"))
                .then().log().all().extract();
    }

    public static void 메이트_제안_요청됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static ExtractableResponse<Response> 메이트_제안_수락_요청(UUID projectId, Long reviewerId) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(getURI(projectId, reviewerId, "join"))
                .then().log().all().extract();
    }

    public static void 메이트_연결됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private static String getURI(UUID projectId, Long reviewerId, String control) {
        return String.format("%s/%s/%d/%s", ENDPOINT, projectId, reviewerId, control);
    }
}
