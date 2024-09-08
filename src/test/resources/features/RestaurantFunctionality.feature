# language: en
Feature: API - Restaurant

  @smoke
  Scenario: Create a new restaurant
    When I request the creation of a new restaurant
    Then the restaurant is saved successfully


