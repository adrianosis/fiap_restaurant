# language: en
Feature: API - Customer

  @smoke
  Scenario: Create a new customer
    When I request the creation of a new customer
    Then the customer is saved successfully

  Scenario: Get an existing customer by ID
    Given a customer has already been created
    When I request to retrieve the customer
    Then the customer is displayed successfully

  Scenario: List existing customers
    Given a customer has already been created
    When I request the list of customers
    Then the customers are displayed successfully

  Scenario: Delete an existing customer
    Given a customer has already been created
    When I request the deletion of the customer
    Then the customer is removed successfully
