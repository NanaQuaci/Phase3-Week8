package com.projects.pages;

import com.projects.base.BasePage;
import com.projects.util.CheckoutInfo;

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
        return $(".sweet-alert, #orderModal, .modal-body, .confirm").is(visible);
    }

    public boolean isErrorDisplayed() {
        return $(".error, .invalid-feedback, .help-block").is(visible);
    }
}
