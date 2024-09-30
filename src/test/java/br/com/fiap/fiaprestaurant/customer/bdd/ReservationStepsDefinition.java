package br.com.fiap.fiaprestaurant.customer.bdd;

import br.com.fiap.fiaprestaurant.reservation.domain.entity.ReservationStatus;
import br.com.fiap.fiaprestaurant.reservation.infra.controller.ChangeReservationRequestDto;
import br.com.fiap.fiaprestaurant.reservation.infra.controller.ReservationDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static br.com.fiap.fiaprestaurant.reservation.utils.ReservationHelper.createReserveRestaurantRequest;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;


public class ReservationStepsDefinition {

    private final String BASE_ENDPOINT = "http://localhost:8080/reservation";

    private Response response;

    private ReservationDto restaurantResponse;

    // Helper method to configure RestAssured request
    private void setupRequest(Object body) {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when()
                .post(BASE_ENDPOINT);
    }

    @When("I request the reserve a restaurant")
    public ReservationDto reserveRestaurant() {
        var reserveRestaurantRequest = createReserveRestaurantRequest();

        setupRequest(reserveRestaurantRequest);

        return response.then().extract().as(ReservationDto.class);
    }

    @Then("the restaurant is reserved successfully")
    public void restaurantReservedSuccess() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReservationResponseSchema.json"));
    }


    @Given("a reservation has already been created")
    public void reservationAlreadyExists() {
        restaurantResponse = reserveRestaurant();
    }

    @When("I request to change a reservation")
    public void changeReservation() {
        var changeReservationRequest = new ChangeReservationRequestDto(
                ReservationStatus.CANCELLED, null
        );

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(changeReservationRequest)
                .when()
                .put(BASE_ENDPOINT + "/{id}", restaurantResponse.getId());
    }

    @Then("the reservation is changed successfully")
    public void verifyReservationChangedSuccess() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/RestaurantResponseSchema.json"))
                .body("status", equalTo(ReservationStatus.CANCELLED.toString()));
    }


    @When("I request the list of opened reservations by restaurant")
    public void retrieveOpenedReservations() {
        LocalDateTime startDateTime = LocalDateTime.of(2024, 9, 15, 18, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 9, 15, 20, 0);

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(BASE_ENDPOINT + "/restaurant/{restaurantId}/opened?startDateTime={startDateTime}&endDateTime={endDateTime}",
                        1L, startDateTime.toString(), endDateTime.toString());
    }

    @Then("the opened reservations are displayed successfully")
    public void verifyOpenedReservationsRetrieved() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReservationArrayResponseSchema.json"));
    }


    @When("I request the list of completed reservations by customer")
    public void retrieveCompletedReservations() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(BASE_ENDPOINT + "/customer/{customerId}/completed", 1L);
    }

    @Then("the completed reservations are displayed successfully")
    public void verifyCompletedReservationsRetrieved() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReservationArrayResponseSchema.json"));
    }

}
