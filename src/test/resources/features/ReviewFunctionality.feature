# language: en
Feature: API - Review

  Scenario: Create a new review
    Given a customer has already been created
    Given a restaurant has already been created
    Given a reservation has already been created
    Given I request to change a reservation to in progress
    Given the reservation is changed to in progress successfully
    Given I request to change a reservation to completed
    When I request the creation of a new review
    Then the review is saved successfully

  Scenario: Get an existing review by ID
    Given a customer has already been created
    Given a restaurant has already been created
    Given a reservation has already been created
    Given I request to change a reservation to in progress
    Given the reservation is changed to in progress successfully
    Given I request to change a reservation to completed
    Given I request the creation of a new review
    When I request to retrieve the review
    Then the review is displayed successfully

  Scenario: Delete an existing review
    Given a customer has already been created
    Given a restaurant has already been created
    Given a reservation has already been created
    Given I request to change a reservation to in progress
    Given the reservation is changed to in progress successfully
    Given I request to change a reservation to completed
    Given I request the creation of a new review
    When I request the deletion of the review
    Then the review is removed successfully

  Scenario: List reviews by a specific restaurant
    Given a review has already been created
    When I request the list of opened reviews by restaurant id 1
    Then the opened reviews are displayed successfully

  Scenario: List reviews by a specific customer
    Given a review has already been created
    When I request the list of completed reviews by customer id 1
    Then the completed reviews are displayed successfully
