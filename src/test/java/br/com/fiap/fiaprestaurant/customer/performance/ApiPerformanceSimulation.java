package br.com.fiap.fiaprestaurant.customer.performance;

import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ApiPerformanceSimulation extends Simulation {

    private final HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:8080")
                    .header("Content-type", "application/json");


    ActionBuilder createCustomerRequest = http("add customer")
            .post("/customer")
            .body(StringBody("{ \"name\": \"Paulo\", \"email\": \"paulo@gmail.com\" }"))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("customerId"));


    ActionBuilder findCustomerRequest = http("find customer")
            .get("/customer/#{customerId}")
            .check(status().is(200));

    ActionBuilder listarMensagemRequest = http("list customers")
            .get("/customer")
            .check(status().is(200));

    ActionBuilder removerCustomerRequest = http("remover mensagem")
            .delete("/customer/#{customerId}")
            .check(status().is(204));


    ScenarioBuilder scenarioCreateCustomer = scenario("Create customer")
            .exec(createCustomerRequest);

    ScenarioBuilder scenarioListCustomers = scenario("List Customer")
            .exec(listarMensagemRequest);

    ScenarioBuilder scenarioAddSearchCustomer = scenario("Add and Search Customer")
            .exec(createCustomerRequest)
            .exec(findCustomerRequest);

    ScenarioBuilder scenarioAddRemoveCustomer = scenario("Add and Remove Customer")
            .exec(createCustomerRequest)
            .exec(removerCustomerRequest);

    {
        setUp(
                scenarioCreateCustomer.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(1))),
                scenarioAddRemoveCustomer.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(1))),
                scenarioAddSearchCustomer.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(1)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(6)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(1))),
                scenarioListCustomers.injectOpen(
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

