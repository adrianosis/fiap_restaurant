package br.com.fiap.fiaprestaurant.customer.bdd;

import br.com.fiap.fiaprestaurant.restaurant.infra.controller.RestaurantDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurantRequest;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class RestaurantStepsDefinition {

    private final String BASE_ENDPOINT = "http://localhost:8080/restaurant";

    private Response response;

    private RestaurantDto restaurantResponse;

    // Helper method to configure RestAssured request
    private void setupRequest(Object body) {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when()
                .post(BASE_ENDPOINT);
    }

    @When("I request the creation of a new restaurant")
    public RestaurantDto createNewRestaurant() {
        var restaurantRequest = createRestaurantRequest();

        setupRequest(restaurantRequest);

        return response.then().extract().as(RestaurantDto.class);
    }

    @Then("the restaurant is saved successfully")
    public void restaurantSavedSuccess() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/RestaurantResponseSchema.json"));
    }


    @Given("a restaurant has already been created")
    public void restaurantAlreadyExists() {
        restaurantResponse = createNewRestaurant();
    }

    @When("I request to retrieve the restaurant")
    public void retrieveRestaurant() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(BASE_ENDPOINT + "/{id}", restaurantResponse.getId());
    }

    @Then("the restaurant is displayed successfully")
    public void verifyRestaurantRetrieved() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/RestaurantResponseSchema.json"));
    }


    @When("I request the list of restaurants by name or location or kitchen type")
    public void retrieveRestaurants() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(BASE_ENDPOINT + "?name=%&location=%&kitchenType=%");
    }

    @Then("the restaurants are displayed successfully")
    public void verifyRestaurantsRetrieved() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/RestaurantArrayResponseSchema.json"));
    }

}
