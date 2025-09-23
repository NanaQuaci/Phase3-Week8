package com.projects.stepdefinitions;

import io.cucumber.java.en.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CheckoutSteps {

    @Given("the user has 1 item in cart")
    public void userHasOneItemInCart() {
        // lightweight implementation for POC: add a known product
        open("/");
        $$(".hrefch").first().click();
        $$(".btn").findBy(text("Add to cart")).click();
        // accept alert if present
        try { confirm(); } catch(Exception ignored) {}
    }

    @When("the user fills the order form with name {string} and credit card {string}")
    public void fillOrderForm(String name, String cc) {
        open("/cart.html");
        // click place order button - update selector if necessary
        $$(".btn").findBy(text("Place Order")).click();
        // fill modal form - selectors are example placeholders
        $("#name").setValue(name);
        $("#card").setValue(cc);
    }

    @When("the user clicks the \"Place Order\" button")
    public void clickPlaceOrder() {
        $$(".btn").findBy(text("Purchase")).click();
    }

    @Then("the order confirmation should be displayed")
    public void orderConfirmationDisplayed() {
        $("div#order-confirmation, .sweet-alert").shouldBe(visible);
    }

    @Then("the form should prevent submission and show an error message")
    public void invalidCcShowsError() {
        // assert validation message visible - placeholder
        $(".error, .invalid-feedback").shouldBe(visible);
    }
}
