# language: en
Feature: API - Restaurant

  Scenario: Create a new restaurant
    When I request the creation of a new restaurant
    Then the restaurant is saved successfully

  Scenario: Update a restaurant
    Given a restaurant has already been created
    When I request an update for a restaurant
    Then the restaurant is updated successfully

  Scenario: Get an existing restaurant by ID
    Given a restaurant has already been created
    When I request to retrieve the restaurant
    Then the restaurant is displayed successfully

  Scenario: List existing restaurants by name or location or kitchen type
    Given a restaurant has already been created
    When I request the list of restaurants by name or location or kitchen type
    Then the restaurants are displayed successfully
