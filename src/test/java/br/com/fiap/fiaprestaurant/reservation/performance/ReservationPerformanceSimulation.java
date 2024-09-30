package br.com.fiap.fiaprestaurant.reservation.performance;

import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ReservationPerformanceSimulation extends Simulation {

    private final HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:8080")
                    .header("Content-type", "application/json");


    ActionBuilder reserveRestaurantRequest = http("reserve restaurant")
            .post("/reservation")
            .body(StringBody("""
                    {
                      "reservationDateTime": "2024-10-29T00:42:38.053Z",
                      "guests": 1,
                      "restaurantId": 2,
                      "customerId": 1
                    }"""))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("reservationId"));

    ActionBuilder changeReservationRequest = http("change reservation")
            .put("/reservation/#{reservationId}")
            .body(StringBody("""
                    {
                      "status": "CANCELLED",
                      "tableTag": "A01"
                    }"""))
            .check(status().is(200));

    ActionBuilder listOpenedReservationsRequest = http("list opened reservations")
            .get("/reservation/restaurant/1/opened?startDateTime=2024-09-01T00:00&endDateTime=2024-12-31T23:59")
            .check(status().is(200));

    ActionBuilder listCompletedReservationsRequest = http("list completed reservations")
            .get("/reservation/customer/1/completed")
            .check(status().is(200));

    ScenarioBuilder scenarioReserveRestaurant = scenario("Reserve restaurant")
            .exec(reserveRestaurantRequest);

    ScenarioBuilder scenarioReserveAndChangeReservation = scenario("Reserve and Change reservation")
            .exec(reserveRestaurantRequest)
            .exec(changeReservationRequest);

    ScenarioBuilder scenarioListOpenedReservations = scenario("List opened reservations")
            .exec(listOpenedReservationsRequest);

    ScenarioBuilder scenarioListCompletedReservations = scenario("List completed reservations")
            .exec(listCompletedReservationsRequest);

    {
        setUp(
                scenarioReserveRestaurant.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(1))),
                scenarioReserveAndChangeReservation.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(1))),
                scenarioListOpenedReservations.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(1))),
                scenarioListCompletedReservations.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(1)))
        )
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(500),
                        global().failedRequests().count().is(0L));
    }
}

