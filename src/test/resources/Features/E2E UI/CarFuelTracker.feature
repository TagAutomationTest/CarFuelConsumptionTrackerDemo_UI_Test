Feature: Car Fuel Consumption Tracker Service

  @CarFuel @ValidateForm
  Scenario:As a user ,I want to verify that all car details fields displayed properly on UI
    Given  User Open Car Fuel Webpage
    And Validate that "Page header" field is displayed properly on UI
    And Validate that "Car Number" field is displayed properly on UI
    And Validate that "Fuel in Liters" field is displayed properly on UI
    And Validate that "Fuel Cost" field is displayed properly on UI
    And Validate that "Refill Date Picker" field is displayed properly on UI
    Then Validate that "Customer Company ID" field is displayed properly on UI

  @CarFuel   @AddRecord
  Scenario: As a user ,I want to add new Car record
    Given User Open Car Fuel Webpage
    When Add 1 car record
    Then validate that car records added properly


  @CarFuel   @AddRecord   @MultipleRecords
  Scenario:As a user ,I want to add multiple Car records
    Given User Open Car Fuel Webpage
    When Add 5 car record
    Then validate that car records added properly

  @CarFuel    @deleteRecord  @MultipleRecords   @deletAll
  Scenario:As a user ,I want to delete all car records already added
    Given User Open Car Fuel Webpage
    And Add 5 car record
    When validate that car records added properly
    Then Delete car records from Grid
    And validate that all records deleted properly




