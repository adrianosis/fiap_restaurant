# language: en
Feature: API - Reservation

  @smoke
  Scenario: Reserve a restaurant
    Given a customer has already been created
    Given a restaurant has already been created
    When I request the reserve a restaurant
    Then the restaurant is reserved successfully

  Scenario: Change reservation
    Given a reservation has already been created
    When I request to change a reservation to in progress
    Then the reservation is changed to in progress successfully
    When I request to change a reservation to completed
    Then the reservation is changed to completed successfully

  Scenario: List existing opened reservations by restaurant
    Given a reservation has already been created
    When I request the list of opened reservations by restaurant
    Then the opened reservations are displayed successfully

  Scenario: List existing completed reservations by customer
    Given a reservation has already been created
    When I request the list of completed reservations by customer
    Then the completed reservations are displayed successfully