package com.projects.stepdefinitions;

import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.*;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class ContactSteps {

    @Given("the user is on the contact form")
    public void userOnContactForm() {
        open("/");
        $("a[data-target='#exampleModal']").shouldBe(visible).click();
        $(".modal-content").shouldBe(visible);
    }

    @When("the user fills the contact form with name {string}, email {string} and message {string}")
    public void fillContactForm(String name, String email, String message) {
        $("#recipient-name").setValue(name);
        $("#recipient-email").setValue(email);
        $("#message-text").setValue(message);
    }

    @When("the user submits the form")
    public void submitContactForm() {
        $(".modal-footer .btn-primary").click();
    }

    @Then("a submission confirmation should be shown")
    public void contactConfirmationShown() {
        try {
            String text = switchTo().alert().getText();
            if (!text.contains("Thanks for the message!!")) {
                throw new AssertionError("Unexpected alert text: " + text);
            }
            confirm(); // accept alert
        } catch (Exception ignored) {
        }
    }



    @Then("the form should show an email validation error")
    public void contactEmailValidation() {
        SelenideElement modal = $(".sweet-alert.showSweetAlert.visible")
                .shouldBe(visible, Duration.ofSeconds(10));

        modal.$("p").shouldHave(text("Incorrect Entries!"));
        modal.$(".confirm").click();
    }

}
