package br.com.fiap.fiaprestaurant.reservation.infra.controller;

import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
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
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.createReserveRestaurantRequest;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Fail.fail;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReservationControllerIT {

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
    void shouldReserveRestaurant(){
        var reserveRestaurantRequest = createReserveRestaurantRequest();

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reserveRestaurantRequest)
                .when()
                .post("/reservation")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReservationResponseSchema.json"))
                .body("guests", equalTo(reserveRestaurantRequest.getGuests()))
                .body("reservationDateTime", equalTo(reserveRestaurantRequest.getReservationDateTime().format(DateTimeFormatter.ISO_DATE_TIME)));
    }

    @Test
    void shouldChangeReservation(){
        long reservationId = 2L;
        ChangeReservationRequestDto changeReservationRequest = new ChangeReservationRequestDto(
                ReservationStatus.IN_PROGRESS, "A05"
        );

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(changeReservationRequest)
                .when()
                .put("/reservation/{reservationId}", reservationId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReservationResponseSchema.json"))
                .body("status", equalTo(changeReservationRequest.getStatus().toString()))
                .body("tableTag", equalTo(changeReservationRequest.getTableTag()));
    }

    @Test
    void shouldFindAllOpenedReservationsByRestaurantAndReservationDateTimeId() {
        long restaurantId = 1L;
        LocalDateTime startDateTime = LocalDateTime.of(2024, 9, 15, 18, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 9, 15, 20, 0);

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .queryParam("startDateTime", startDateTime.format(DateTimeFormatter.ISO_DATE_TIME))
                .queryParam("endDateTime", endDateTime.format(DateTimeFormatter.ISO_DATE_TIME))
                .get("/reservation/restaurant/{restaurantId}/opened", restaurantId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReservationArrayResponseSchema.json"));
    }

    @Test
    void shouldFindAllCompletedReservationsByCustomerIdUseCase() {
        long customerId = 1L;

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/reservation/customer/{customerId}/completed", customerId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReservationArrayResponseSchema.json"));
    }

}