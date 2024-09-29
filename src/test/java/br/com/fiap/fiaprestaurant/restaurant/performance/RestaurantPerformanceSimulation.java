package br.com.fiap.fiaprestaurant.restaurant.performance;

import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class RestaurantPerformanceSimulation extends Simulation {

    private final HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:8080")
                    .header("Content-type", "application/json");


    ActionBuilder createRestaurantRequest = http("create restaurant")
            .post("/restaurant")
            .body(StringBody("""
                    {
                      "name": "PIZZA PRIME",
                      "kitchenType": "PIZZA",
                      "capacity": 30000,
                      "openingTime": "08:00",
                      "closingTime": "18:00",
                      "street": "AV IMP LEOPOLDINA",
                      "number": "957",
                      "complement": "LOJA 01",
                      "district": "VILA LEOPOLDINA",
                      "city": "SAO PAULO",
                      "state": "SP",
                      "postalCode": "05316900"
                    }"""))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("restaurantId"));

    ActionBuilder updateRestaurantRequest = http("update restaurant")
            .put("/restaurant/#{restaurantId}")
            .body(StringBody("""
                    {
                      "name": "META PIZZA",
                      "kitchenType": "PIZZA",
                      "capacity": 20000,
                      "openingTime": "10:00",
                      "closingTime": "23:00",
                      "street": "AV IMP LEOPOLDINA",
                      "number": "900",
                      "complement": "NT",
                      "district": "VILA LEOPOLDINA",
                      "city": "SAO PAULO",
                      "state": "SP",
                      "postalCode": "05316900"
                    }"""))
            .check(status().is(200));

    ActionBuilder findRestaurantRequest = http("find restaurant")
            .get("/restaurant/#{restaurantId}")
            .check(status().is(200));

    ActionBuilder listRestaurantRequest = http("list restaurant")
            .get("/restaurant?name=%25&location=%25&kitchenType=%25")
            .check(status().is(200));

    ScenarioBuilder scenarioCreateRestaurant = scenario("Create restaurant")
            .exec(createRestaurantRequest);

    ScenarioBuilder scenarioUpdateRestaurant = scenario("Update restaurant")
            .exec(createRestaurantRequest)
            .exec(updateRestaurantRequest);

    ScenarioBuilder scenarioCreateAndSearchRestaurant = scenario("Create and Search restaurant")
            .exec(createRestaurantRequest)
            .exec(findRestaurantRequest);

    ScenarioBuilder scenarioListRestaurant = scenario("List restaurant")
            .exec(listRestaurantRequest);


    {
        setUp(
                scenarioCreateRestaurant.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(1))),
                scenarioUpdateRestaurant.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(1))),
                scenarioCreateAndSearchRestaurant.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(1))),
                scenarioListRestaurant.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(1))))
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(500),
                        global().failedRequests().count().is(0L));
    }
}

