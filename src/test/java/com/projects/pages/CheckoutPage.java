package com.projects.pages;

import com.projects.base.BasePage;
import com.projects.util.CheckoutInfo;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CheckoutPage extends BasePage {

    public void placeOrder() {
        $$(".btn, a").findBy(text("Place Order")).click();
    }

    public void fillCheckoutForm(CheckoutInfo info) {
        $("#name").setValue(info.getName());
        $("#card").setValue(info.getCreditCard());
    }

    public void submitOrder() {
        $$(".btn, a").findBy(text("Purchase")).click();
    }

    public boolean isOrderConfirmed() {
        return $(".sa-success").shouldBe(visible, Duration.ofSeconds(10))
                .exists()
                && $("h2").shouldBe(visible, Duration.ofSeconds(10))
                .getText()
                .equals("Thank you for your purchase!");
    }

    public boolean isErrorDisplayed() {
        return $(".sweet-alert").shouldBe(visible, Duration.ofSeconds(10))
                .getText()
                .contains("Please fill out Name and Creditcard.");
    }
}
