package com.projects.stepdefinitions;

import io.cucumber.java.en.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class ContactSteps {

    @Given("the user is on the contact form")
    public void userOnContactForm() {
        open("/");
        // click contact nav link - modify selector per AUT
        $$(".nav").findBy(text("Contact")).click();
    }

    @When("the user fills the contact form with name {string}, email {string} and message {string}")
    public void fillContactForm(String name, String email, String message) {
        $("#recipient-name").setValue(name);
        $("#recipient-email").setValue(email);
        $("#message-text").setValue(message);
    }

    @When("the user submits the form")
    public void submitContactForm() {
        $$(".btn").findBy(text("Send message")).click();
    }

    @Then("a submission confirmation should be shown")
    public void contactConfirmationShown() {
        $(".modal-body").shouldHave(text("Thanks"));
    }

    @Then("the form should show an email validation error")
    public void contactEmailValidation() {
        $(".invalid-feedback").shouldBe(visible);
    }
}
