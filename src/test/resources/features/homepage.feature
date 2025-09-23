Feature: Homepage
  As a Customer
  I want to access the Product Store homepage
  So that I can browse available products

  @smoke @homepage
  Scenario: Homepage loads and shows product list
    Given the user is on the homepage
    Then the homepage should display a list of products
    And at least one product should show name, price and thumbnail

  @regression
  Scenario Outline: Product details navigation
    Given the user is on the homepage
    When the user clicks the product "<product>"
    Then the product detail page shows the product name "<product>"

    Examples:
      | product              |
      | Sony vaio i5         |
      | Samsung galaxy s6    |
      | MacBook air          |
