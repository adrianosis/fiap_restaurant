package br.com.fiap.fiaprestaurant.customer.infra.controller;


import br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import io.qameta.allure.restassured.AllureRestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CustomerControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void shouldCreateCustomer() {
        var customerRequest = CustomerHelper.createCustomerEntity();

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customerRequest)
                .when()
                .post("/customer")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
                .body("$", hasKey("name"))
                .body("$", hasKey("email"))
                .body("name", equalTo(customerRequest.getName()))
                .body("email", equalTo(customerRequest.getEmail()));
    }

    @Test
    @DirtiesContext
    void shouldDeleteCustomerById() {
        var id = 1;
        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/customer/{id}", id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
               // .body(equalTo("mensagem removida"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldFindCustomerById() {
        var id = 1L;
        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customer/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value());
        //.body(matchesJsonSchemaInClasspath("./schemas/MensagemResponseSchema.json"));
    }

    @Test
    void shouldFindAllCustomers(){
        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customer")
                .then()
                .statusCode(HttpStatus.OK.value());
        //.body(matchesJsonSchemaInClasspath("./schemas/MensagemResponseSchema.json"));
    }

}
