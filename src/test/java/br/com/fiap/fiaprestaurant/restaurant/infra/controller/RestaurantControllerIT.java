package br.com.fiap.fiaprestaurant.restaurant.infra.controller;

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

import java.time.format.DateTimeFormatter;

import static br.com.fiap.fiaprestaurant.restaurant.infra.utils.RestaurantHelper.createRestaurantRequest;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RestaurantControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void shouldCreateRestaurant(){
        var restaurantRequest = createRestaurantRequest();

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restaurantRequest)
                .when()
                .post("/restaurant")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/RestaurantResponseSchema.json"))
                .body("name", equalTo(restaurantRequest.getName()))
                .body("kitchenType", equalTo(restaurantRequest.getKitchenType()))
                .body("capacity", equalTo(restaurantRequest.getCapacity()))
                .body("openingTime", equalTo(restaurantRequest.getOpeningTime().format(DateTimeFormatter.ISO_TIME)))
                .body("closingTime", equalTo(restaurantRequest.getClosingTime().format(DateTimeFormatter.ISO_TIME)))
                .body("street", equalTo(restaurantRequest.getStreet()))
                .body("number", equalTo(restaurantRequest.getNumber()))
                .body("complement", equalTo(restaurantRequest.getComplement()))
                .body("district", equalTo(restaurantRequest.getDistrict()))
                .body("city", equalTo(restaurantRequest.getCity()))
                .body("state", equalTo(restaurantRequest.getState()))
                .body("postalCode", equalTo(restaurantRequest.getPostalCode()));
    }


    @Autowired
    ObjectMapper objectMapper;

    public String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
