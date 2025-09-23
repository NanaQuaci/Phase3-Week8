Feature: Contact form
  As a Customer
  I want to send messages to the store

  @regression @contact
  Scenario: Submit contact form with valid data
    Given the user is on the contact form
    When the user fills the contact form with name "Collins", email "collins@example.com" and message "Test message"
    And the user submits the form
    Then a submission confirmation should be shown

  Scenario: Contact form validation - invalid email
    Given the user is on the contact form
    When the user fills the contact form with name "Collins", email "bademail" and message "Hi"
    Then the form should show an email validation error
