package br.com.fiap.fiaprestaurant.review.infra.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static br.com.fiap.fiaprestaurant.review.utils.ReviewHelper.createReview;
import static br.com.fiap.fiaprestaurant.review.utils.ReviewHelper.createReviewDTORequest;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReviewControllerIT {


    @LocalServerPort
    private int port;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }


    @Test
    void shouldCreateReview() {
        var reviewRequest = createReviewDTORequest();

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reviewRequest)
                .when()
                .post("/review")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReviewResponseSchema.json"))
                .body("score", equalTo(reviewRequest.getScore()))
                .body("comment", equalTo(reviewRequest.getComment()))
                .body("reservationId", equalTo(reviewRequest.getReservationId()));
    }

    @Test
    void shouldFindReviewById() {
        var id = 1L;

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/review/{reviewId}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReviewFindResponseSchema.json"));
    }

    @Test
    void shouldDeleteReviewById() {

        var id = 1;

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/review/{id}", id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void shouldFindAllReviewsByCustomerId() {
        var id = 1L;

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/review/customer/{customerId}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReviewArrayResponseSchema.json"));
    }

    @Test
    void shouldFindAllReviewsByRestaurantId() {
        var id = 1L;

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/review/restaurant/{restaurantId}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReviewArrayResponseSchema.json"));
    }

}
