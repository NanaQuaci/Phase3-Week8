package com.projects.pages;

import com.projects.base.BasePage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class ProductPage extends BasePage {

    // Navigate directly to a product by name (from homepage listing)
    public void openProduct(String productName) {
        $$("h4.card-title a").findBy(text(productName)).click();
    }

    // Verify product detail page shows correct product name
    public void verifyProductDetailPage(String productName) {
        $("h2").shouldHave(text(productName));
    }

    // Generic button click handler (e.g., "Add to cart")
    public void clickAddToCart(String buttonLabel) {
        $$("a").findBy(text(buttonLabel)).click();
    }

    // Always click "Add to cart"
    public void addToCart() {
        $("a.btn.btn-success.btn-lg")
                .shouldHave(text("Add to cart"))
                .click();
        try {
            confirm();
        } catch (Exception ignored) {
        }
    }


    // Confirmation toast or alert
    public void shouldShowAddToCartConfirmation() {
        var alert = switchTo().alert();
        String alertText = alert.getText();
        if (!alertText.contains("Product added")) {
            throw new AssertionError("Expected alert text to contain 'Product added', but got: " + alertText);
        }
        alert.accept();
    }
}
