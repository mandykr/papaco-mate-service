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

    public static ExtractableResponse<Response> 메이트_제안_수락_요청(ExtractableResponse<Response> response) {
        String uri = response.header("Location");

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(getURI(uri, "join"))
                .then().log().all().extract();
    }

    public static void 메이트_연결됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> 메이트_종료_요청(ExtractableResponse<Response> response) {
        String uri = response.header("Location");

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(getURI(uri, "finish"))
                .then().log().all().extract();
    }

    public static void 메이트_종료됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> 메이트_제안_거절_요청(ExtractableResponse<Response> response) {
        String uri = response.header("Location");

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(getURI(uri, "reject"))
                .then().log().all().extract();
    }

    public static void 메이트_제안_거절됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> 메이트_제안_삭제_요청(ExtractableResponse<Response> response) {
        String uri = response.header("Location");

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(uri)
                .then().log().all().extract();
    }

    public static void 메이트_제안_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private static String getURI(UUID projectId, Long reviewerId, String control) {
        return String.format("%s/%s/%d/%s", ENDPOINT, projectId, reviewerId, control);
    }

    private static String getURI(String uri, String control) {
        return String.format("%s/%s", uri, control);
    }
}
