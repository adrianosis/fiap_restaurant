package br.com.fiap.fiaprestaurant.restaurant.bdd;

import br.com.fiap.fiaprestaurant.restaurant.infra.controller.RestaurantDto;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static br.com.fiap.fiaprestaurant.restaurant.utils.RestaurantHelper.createRestaurantRequest;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class RestaurantStepsDefinition {

    private Response response;

//    private Restaurant restaurantResponse;

    private String ENDPOINT_RESTAURANT = "http://localhost:8080/restaurant";


    @When("I request the creation of a new restaurant")
    public RestaurantDto createNewRestaurant() {
        var restaurantRequest = createRestaurantRequest();

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restaurantRequest)
                .when().post(ENDPOINT_RESTAURANT);

        return response.then().extract().as(RestaurantDto.class);
    }

    @Then("the restaurant is saved successfully")
    public void restaurantSavedSuccess() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/RestaurantResponseSchema.json"));
    }

//    @Given("a customer has already been created")
//    public void mensagemJaPublicada() {
//        mensagemResponse = submeterNovaMensagem();
//    }
//
//    @When("I request to retrieve the customer")
//    public void requisitarBuscarMensagem() {
//        response = given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .get("/customer/{id}", mensagemResponse.getId());
//    }
//
//
//    @Then("the customer is displayed successfully")
//    public void mensagemExibidaComSucesso() {
//        response.then()
//                .statusCode(HttpStatus.OK.value())
//                .body(matchesJsonSchemaInClasspath("./schemas/CustomerResponseSchema.json"));
//    }
//
//
//    @When("I request the list of customers")
//    public void requisitarListaMensagens() {
//        response = given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .get("/customer");
//    }
//
//    @Then("the customers are displayed successfully")
//    public void mensagensSaoExibidasComSucesso() {
//        response.then()
//                .statusCode(HttpStatus.OK.value())
//                .body(matchesJsonSchemaInClasspath("./schemas/FindAllCustomerResponseSchema.json"));
//    }
//
//    @When("I request the deletion of the customer")
//    public void requisitarExclusaoDaMensagem() {
//        response = given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .delete("/customer/{id}", mensagemResponse.getId());
//    }
//
//    @Then("the customer is removed successfully")
//    public void mensagemRemovidaComSucesso() {
//        response.then()
//                .statusCode(HttpStatus.NO_CONTENT.value());
//    }

}
