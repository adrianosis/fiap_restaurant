package br.com.fiap.fiaprestaurant.customer.bdd;

import br.com.fiap.fiaprestaurant.review.infra.controller.ReviewResponseDto;
import br.com.fiap.fiaprestaurant.review.utils.ReviewHelper;
import io.cucumber.java.en.Given;
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
    private String reviewId;

    private void setUpRequest(Object body) {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when()
                .post(BASE_ENDPOINT);
    }

    @When("I request the creation of a new review")
    public ReviewResponseDto createNewReview() {
        var reviewRequest = ReviewHelper.createReviewDTORequest();
        setUpRequest(reviewRequest);
        return response.then().extract().as(ReviewResponseDto.class);
    }

    @Then("the review is saved successfully")
    public void verifyReviewSaved() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReviewSchema.json"));
    }

    @Given("a review has already been created")
    public void givenReviewAlreadyCreated() {
        createNewReview();
    }

    @When("I request to retrieve the review")
    public void requestToRetrieveReview(String reviewId) {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(BASE_ENDPOINT + "/{reviewId}", reviewId);
    }

    @Then("the review is displayed successfully")
    public void verifyReviewDisplayed() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReviewResponseSchema.json"));
    }

    @When("I request the deletion of the review")
    public void deleteReview(String reviewId) {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(BASE_ENDPOINT + "/{id}", reviewId);
    }

    @Then("the review is removed successfully")
    public void verifyReviewDeleted() {
        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @When("I request the list of opened reviews by restaurant")
    public void requestOpenedReviewsByRestaurant(String restaurantId) {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(BASE_ENDPOINT + "/restaurant/{restaurantId}", restaurantId);
    }

    @Then("the opened reviews are displayed successfully")
    public void verifyOpenedReviewsDisplayed() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReviewListResponseSchema.json"));
    }

    @When("I request the list of completed reviews by customer")
    public void requestCompletedReviewsByCustomer(String customerId) {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(BASE_ENDPOINT + "/customer/{customerId}", customerId);
    }

    @Then("the completed reviews are displayed successfully")
    public void verifyCompletedReviewsDisplayed() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ReviewListResponseSchema.json"));
    }
}
