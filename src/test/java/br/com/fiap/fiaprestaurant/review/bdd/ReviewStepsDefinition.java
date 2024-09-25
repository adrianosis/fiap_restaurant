package br.com.fiap.fiaprestaurant.review.bdd;

import br.com.fiap.fiaprestaurant.review.infra.controller.ReviewResponseDto;
import br.com.fiap.fiaprestaurant.review.utils.ReviewHelper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ReviewStepsDefinition {

    private static final String BASE_ENDPOINT = "http://localhost:8080/review";
    private Response response;
    private ReviewResponseDto reviewResponse;

    private void setUpRequest(Object body) {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when()
                .post(BASE_ENDPOINT);
    }

    @When("I request the creation of a new review")
    public ReviewResponseDto createNewReview() {
        var reviewRequest = ReviewHelper.createReviewInput();
        setUpRequest(reviewRequest);
        return response.then().extract().as(ReviewResponseDto.class);
    }

    @Then("the review is saved successfully")
    public void verifyReviewSaved() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReviewResponseSchema.json"));
    }

    @When("I request to delete the review")
    public void deleteReview() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(BASE_ENDPOINT + "/{id}", reviewResponse.getId());
    }

    @Then("the review is removed successfully")
    public void verifyReviewDeleted() {
        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

}
