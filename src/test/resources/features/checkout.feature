Feature: Place Order (Checkout)
  As a Customer
  I want to place an order so I receive a confirmation

  @regression @checkout
  Scenario Outline: Place order with valid details
    Given the user has 1 item in cart
    When the user fills the order form with name "<name>" and credit card "<cc>"
    And the user clicks the "Place Order" button
    Then the order confirmation should be displayed

    Examples:
      | name          | cc                |
      | Collins Adu   | 4242424242424242  |
      | Alice Johnson | 1111222233334444  |

  @regression
  Scenario: Try to place order with invalid credit card
    Given the user has 1 item in cart
    When the user fills the order form with name "Test User" and credit card ""
    Then the form should prevent submission and show an error message
