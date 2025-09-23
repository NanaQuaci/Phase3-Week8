Feature: Cart
  As a Customer
  I want to add and manage items in my shopping cart

  @smoke @cart
  Scenario: Add single product to cart
    Given the user is on the product detail page for "Sony vaio i5"
    When the user clicks "Add to cart"
    Then a confirmation should be shown
    And the cart should contain 1 item

  @regression
  Scenario: Add multiple items and remove one
    Given the user has added "Sony vaio i5" to the cart
    And the user has added "Sony vaio i5" to the cart
    When the user goes to the cart page
    Then the quantity for "Sony vaio i5" should be 2
    When the user removes "Sony vaio i5"
    Then the cart should not contain "Sony vaio i5"
