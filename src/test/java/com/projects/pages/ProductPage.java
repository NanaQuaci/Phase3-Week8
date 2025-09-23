package com.projects.pages;

import com.projects.base.BasePage;

import static com.codeborne.selenide.Selenide.*;       // $, $$, open(), confirm(), etc.
import static com.codeborne.selenide.Condition.*;     // visible, text, etc.

public class ProductPage extends BasePage {

    // Verify product detail page shows correct product name
    public void verifyProductDetailPage(String productName) {
        $("h2").shouldHave(text(productName));
    }

    // Generic button click handler (e.g., "Add to cart")
    public void clickAddToCart(String buttonLabel) {
        $$("a").findBy(text(buttonLabel)).click();
        try {
            confirm(); // handles JS alert if present
        } catch (Exception ignored) {
            // Some AUTs don't trigger an alert; safe to ignore
        }
    }

    // Validate confirmation (toast, modal, or alert substitute)
    public void shouldShowAddToCartConfirmation() {
        $("div#message, .toast, .alert").shouldBe(visible);
    }

    // Convenience method: always click "Add to cart"
    public void addToCart() {
        $$(".btn, a").findBy(text("Add to cart")).click();
        try {
            confirm();
        } catch (Exception ignored) {}
    }
}
