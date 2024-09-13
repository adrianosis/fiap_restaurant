package br.com.fiap.fiaprestaurant.customer.bdd;

import br.com.fiap.fiaprestaurant.customer.infra.controller.CustomerResponseDto;
import br.com.fiap.fiaprestaurant.customer.utils.CustomerHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class StepsDefinition {

    private static final String BASE_ENDPOINT = "http://localhost:8080/customer";
    private Response response;
    private CustomerResponseDto customerResponse;

    // Helper method to configure RestAssured request
    private void setUpRequest(Object body) {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when()
                .post(BASE_ENDPOINT);
    }

    @When("I request the creation of a new customer")
    public CustomerResponseDto createCustomer() {
        var customerRequest = CustomerHelper.createCustomerDTORequest();
        setUpRequest(customerRequest);
        return response.then().extract().as(CustomerResponseDto.class);
    }

    @Then("the customer is saved successfully")
    public void verifyCustomerSaved() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/CustomerResponseSchema.json"));
    }

    @Given("a customer has already been created")
    public void customerAlreadyExists() {
        customerResponse = createCustomer();
    }

    @When("I request to retrieve the customer")
    public void retrieveCustomer() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(BASE_ENDPOINT + "/{id}", customerResponse.id());
    }

    @Then("the customer is displayed successfully")
    public void verifyCustomerRetrieved() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/CustomerResponseSchema.json"));
    }

    @When("I request the list of customers")
    public void retrieveCustomerList() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(BASE_ENDPOINT);
    }

    @Then("the customers are displayed successfully")
    public void verifyCustomerListRetrieved() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/FindAllCustomerResponseSchema.json"));
    }

    @When("I request the deletion of the customer")
    public void deleteCustomer() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(BASE_ENDPOINT + "/{id}", customerResponse.id());
    }

    @Then("the customer is removed successfully")
    public void verifyCustomerDeleted() {
        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
