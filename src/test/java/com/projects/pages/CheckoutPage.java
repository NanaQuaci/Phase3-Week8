package com.projects.pages;

import com.projects.base.BasePage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CheckoutPage extends BasePage {

    public void placeOrder() {
        $$(".btn, a").findBy(text("Place Order")).click();
    }

    public void fillOrderForm(String name, String creditCard) {
        $("#name").setValue(name);
        $("#card").setValue(creditCard);
    }

    public void submitOrder() {
        $$(".btn, a").findBy(text("Purchase")).click();
    }

    public void shouldShowConfirmation() {
        $(".sweet-alert, #orderModal, .modal-body, .confirm").shouldBe(visible);
    }

    public void shouldShowValidationError() {
        $(".error, .invalid-feedback, .help-block").shouldBe(visible);
    }
}
