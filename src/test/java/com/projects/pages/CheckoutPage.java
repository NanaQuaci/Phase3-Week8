package com.projects.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.projects.base.BasePage;
import com.projects.util.CheckoutInfo;
import org.openqa.selenium.NoAlertPresentException;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CheckoutPage extends BasePage {

    public void placeOrder() {
        $$(".btn").findBy(text("Purchase"))
                .shouldBe(Condition.visible, Duration.ofSeconds(2))
                .click();
    }


    public void fillCheckoutForm(CheckoutInfo info) {
        $("#name").setValue(info.getName());
        $("#card").setValue(info.getCreditCard());
    }

    public void submitOrder() {
        $$(".btn, a").findBy(text("Purchase")).click();
    }

    public boolean isOrderConfirmed() {
        SelenideElement alert = $(".sweet-alert.showSweetAlert.visible")
                .shouldBe(visible, Duration.ofSeconds(10));

        // Check success icon
        boolean hasSuccessIcon = alert.$(".sa-success").exists();

        // Check confirmation title (ignore punctuation / case)
        String title = alert.$("h2").getText().trim();
        boolean titleOk = title.equalsIgnoreCase("Thank you for your purchase!")
                || title.equalsIgnoreCase("Thank you for your purchase");

        return hasSuccessIcon && titleOk;
    }


    public boolean isErrorDisplayed() {
        try {
            String alertText = switchTo().alert().getText();
            return alertText.contains("Please fill out Name and Creditcard.");
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}

