package com.projects.stepdefinitions;

import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.*;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CheckoutSteps {

    @Given("the user has 1 item in cart")
    public void userHasOneItemInCart() {
        // Open homepage and add first available product to cart
        open("/");
        $$(".hrefch").first().click();
        $$(".btn").findBy(text("Add to cart")).click();

        // Accept alert if present (confirmation popup)
        try { confirm(); } catch(Exception ignored) {}
    }

    @When("the user fills the order form with name {string} and credit card {string}")
    public void fillOrderForm(String name, String cc) {
        open("/cart.html");
        // Click "Place Order" to open modal
        $$(".btn").findBy(text("Place Order")).click();
        // Fill modal form
        $("#name").setValue(name);
        $("#card").setValue(cc);
    }

    @When("the user clicks the \"Place Order\" button")
    public void clickPlaceOrder() {
        // Click "Purchase" inside modal
        $$(".btn").findBy(text("Purchase")).click();
    }

    @Then("the order confirmation should be displayed")
    public void orderConfirmationDisplayed() {
        // Wait for the modal to appear
        SelenideElement modal = $(".sweet-alert.showSweetAlert.visible")
                .shouldBe(visible, Duration.ofSeconds(10));

        // Verify the title
        modal.$("h2").shouldHave(text("Thank you for your purchase!"));

        // Optionally, verify the details
        modal.$("p.lead").shouldHave(text("Id:")); // simple sanity check

        // Click OK to close
        modal.$(".confirm").click();
    }



    @Then("the form should prevent submission and show an error message")
    public void invalidCcShowsError() {
        SelenideElement modal = $(".sweet-alert.showSweetAlert.visible")
                .shouldBe(visible, Duration.ofSeconds(10));

        modal.$("p").shouldHave(text("Please fill out Name and Creditcard."));
        modal.$(".confirm").click();
    }

}
