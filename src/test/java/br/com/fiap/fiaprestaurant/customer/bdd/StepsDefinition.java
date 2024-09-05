package br.com.fiap.fiaprestaurant.customer.bdd;

import br.com.fiap.fiaprestaurant.customer.domain.entity.Customer;
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
    private Response response;

    private Customer mensagemResponse;

    private String ENDPOINT_MENSAGENS = "http://localhost:8080/customer";


    @When("I request the creation of a new customer")
    public Customer submeterNovaMensagem() {
        var mensagemRequest = CustomerHelper.createCustomer();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(mensagemRequest)
                .when().post(ENDPOINT_MENSAGENS);
        return response.then().extract().as(Customer.class);
    }

    @Then("the customer is saved successfully")
    public void mensagemRegistradaComSucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/CustomerResponseSchema.json"));
    }

    @Given("a customer has already been created")
    public void mensagemJaPublicada() {
        mensagemResponse = submeterNovaMensagem();
    }

    @When("I request to retrieve the customer")
    public void requisitarBuscarMensagem() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customer/{id}", mensagemResponse.getId());
    }


    @Then("the customer is displayed successfully")
    public void mensagemExibidaComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/CustomerResponseSchema.json"));
    }


    @When("I request the list of customers")
    public void requisitarListaMensagens() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customer");
    }

    @Then("the customers are displayed successfully")
    public void mensagensSaoExibidasComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/FindAllCustomerResponseSchema.json"));
    }

    @When("I request the deletion of the customer")
    public void requisitarExclusaoDaMensagem() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/customer/{id}", mensagemResponse.getId());
    }

    @Then("the customer is removed successfully")
    public void mensagemRemovidaComSucesso() {
        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

}
