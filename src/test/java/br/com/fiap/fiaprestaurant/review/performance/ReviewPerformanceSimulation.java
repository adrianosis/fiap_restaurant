package br.com.fiap.fiaprestaurant.review.performance;

import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ReviewPerformanceSimulation extends Simulation {

    private final HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:8080")
                    .header("Content-type", "application/json");


    ActionBuilder createReviewRequest = http("create review")
            .post("/review")
            .body(StringBody("{ \"score\": 5, \"comment\": \"Muito bom\", \"reservationId\": 2 }"))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("reviewId"));

//{
//  "score": 5,
//  "comment": "Muito bom",
//  "reservationId": 1
//}
    ActionBuilder findReviewRequest = http("find review")
            .get("/review/#{reviewId}")
            .check(status().is(200));

    ActionBuilder listReviewsByCustomerRequest = http("list reviews by customer")
            .get("/review/customer/1")
            .check(status().is(200));
    ActionBuilder listReviewByRestaurantRequest = http("list reviews by restaurant")
            .get("/review/restaurant/1")
            .check(status().is(200));

    ActionBuilder removerReviewRequest = http("remove review")
            .delete("/review/#{reviewId}")
            .check(status().is(204));


    ScenarioBuilder scenarioCreateReview = scenario("Create review")
            .exec(createReviewRequest);

    ScenarioBuilder scenarioListReviewsByRestaurant = scenario("List Reviews By Restaurant")
            .exec(listReviewByRestaurantRequest);
    ScenarioBuilder scenarioListReviewsByCustomer = scenario("List Reviews By Customer")
            .exec(listReviewsByCustomerRequest);

    ScenarioBuilder scenarioAddSearchReview = scenario("Add and Search Review")
            .exec(createReviewRequest)
            .exec(findReviewRequest);

    ScenarioBuilder scenarioAddRemoveReview = scenario("Add and Remove Review")
            .exec(createReviewRequest)
            .exec(removerReviewRequest);

    {
        setUp(
                scenarioCreateReview.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(1))),
                scenarioAddRemoveReview.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(1))),
                scenarioAddSearchReview.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(1))),
                scenarioListReviewsByRestaurant.injectOpen(
                        rampUsersPerSec(1)
                                .to(100)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(100)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(100)
                                .to(1)
                                .during(Duration.ofSeconds(1))),
                scenarioListReviewsByCustomer.injectOpen(
                        rampUsersPerSec(1)
                                .to(100)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(100)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(100)
                                .to(1)
                                .during(Duration.ofSeconds(1))))
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(500),
                        global().failedRequests().count().is(0L));
    }
}

