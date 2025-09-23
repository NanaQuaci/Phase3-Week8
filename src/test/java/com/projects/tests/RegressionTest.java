package com.projects.tests;

import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.projects.base.BaseTest;
import com.projects.pages.CartPage;
import com.projects.pages.CheckoutPage;
import com.projects.pages.HomePage;
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
@Feature("Regression Flow")
@ExtendWith({ScreenShooterExtension.class})
public class RegressionTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(RegressionTest.class);
    private final HomePage homepage = new HomePage();
    private final ProductPage productPage = new ProductPage();
    private final CartPage cartPage = new CartPage();
    private final CheckoutPage checkoutPage = new CheckoutPage();

    @Test
    @Story("Happy Path")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Full regression flow: browse → add to cart → checkout → confirmation")
    void testFullPurchaseFlow() {
        String product = TestDataLoader.getProduct("phone");
        CheckoutInfo info = TestDataLoader.getCheckoutInfo("valid");

        log.info("Starting full regression flow");
        homepage.open();
        productPage.openProduct(product);
        productPage.addToCart();

        cartPage.openCart();
        cartPage.proceedToCheckout();

        checkoutPage.fillCheckoutForm(info);
        checkoutPage.placeOrder();

        assertTrue(checkoutPage.isOrderConfirmed(), "Order should be confirmed in regression flow");
    }
}