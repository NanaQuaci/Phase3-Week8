package com.projects.tests;

import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.projects.base.BaseTest;
import com.projects.pages.CartPage;
import com.projects.pages.CheckoutPage;
import com.projects.pages.ProductPage;
import com.projects.util.CheckoutInfo;
import com.projects.util.TestDataLoader;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Product Store UI Tests")
@Feature("Checkout")
@ExtendWith({ScreenShooterExtension.class})
public class CheckoutTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(CheckoutTest.class);
    private final ProductPage productPage = new ProductPage();
    private final CartPage cartPage = new CartPage();
    private final CheckoutPage checkoutPage = new CheckoutPage();

    @Test
    @Story("Valid Order")
    @Severity(SeverityLevel.CRITICAL)
    void testValidCheckout() {
        String product = TestDataLoader.getProduct("laptop");
        CheckoutInfo info = TestDataLoader.getCheckoutInfo("valid");

        log.info("Adding '{}' to cart and checking out with valid details", product);
        productPage.openProduct(product);
        productPage.addToCart();

        cartPage.openCart();
        cartPage.proceedToCheckout();

        checkoutPage.fillCheckoutForm(info);
        checkoutPage.placeOrder();

        assertTrue(checkoutPage.isOrderConfirmed(), "Order should be confirmed");
    }

    @Test
    @Story("Invalid Credit Card")
    @Severity(SeverityLevel.CRITICAL)
    void testInvalidCardCheckout() {
        String product = TestDataLoader.getProduct("laptop");
        CheckoutInfo info = TestDataLoader.getCheckoutInfo("invalidCard");

        log.info("Trying checkout with invalid credit card");
        productPage.openProduct(product);
        productPage.addToCart();

        cartPage.openCart();
        cartPage.proceedToCheckout();

        checkoutPage.fillCheckoutForm(info);
        checkoutPage.placeOrder();

        assertTrue(checkoutPage.isErrorDisplayed(), "Error should be shown for invalid card");
    }
}
