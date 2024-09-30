# language: en
Feature: API - Review

  @smoke
  Scenario: Create a new review
    When I request the creation of a new review
    Then the review is saved successfully

  Scenario: Get an existing review by ID
    Given a review has already been created
    When I request to retrieve the review
    Then the review is displayed successfully

  Scenario: Delete an existing review
    Given a review has already been created
    When I request the deletion of the review
    Then the review is removed successfully

  Scenario: List reviews by restaurant
    Given a review has already been created
    When I request the list of opened reviews by restaurant
    Then the opened reviews are displayed successfully

  Scenario: List reviews by customer
    Given a review has already been created
    When I request the list of completed reviews by customer
    Then the completed reviews are displayed successfully
