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

    @Test
    @Story("Missing Required Fields")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that an error is shown when required checkout fields are missing")
    void testCheckoutWithMissingRequiredFields() {
        String product = TestDataLoader.getProduct("laptop");
        CheckoutInfo incompleteInfo = TestDataLoader.getCheckoutInfo("missingFields"); // e.g., empty name/card

        log.info("Trying checkout with missing required fields");
        productPage.openProduct(product);
        productPage.addToCart();

        cartPage.openCart();
        cartPage.proceedToCheckout();

        checkoutPage.fillCheckoutForm(incompleteInfo);
        checkoutPage.placeOrder();

        assertTrue(checkoutPage.isErrorDisplayed(), "Error should be shown for missing required fields");
    }


    @Test
    @Story("Invalid Expiry Date")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that an error is shown when using an expired credit card")
    void testCheckoutWithExpiredCard() {
        String product = TestDataLoader.getProduct("phone");
        CheckoutInfo expiredCardInfo = TestDataLoader.getCheckoutInfo("expiredCard");

        log.info("Trying checkout with expired card details");
        productPage.openProduct(product);
        productPage.addToCart();

        cartPage.openCart();
        cartPage.proceedToCheckout();

        checkoutPage.fillCheckoutForm(expiredCardInfo);
        checkoutPage.placeOrder();

        assertTrue(checkoutPage.isErrorDisplayed(), "Error should be displayed for expired card");
    }


    @Test
    @Story("Empty Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that checkout is not allowed when the cart is empty")
    void testCheckoutWithEmptyCart() {
        log.info("Attempting checkout with an empty cart");
        cartPage.openCart();
        cartPage.proceedToCheckout();

        assertTrue(cartPage.isEmptyCartMessageVisible() || checkoutPage.isErrorDisplayed(),
                "User should not be able to proceed to checkout with an empty cart");
    }

    @Test
    @Story("Special Characters in Form")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that checkout handles special characters or long input gracefully")
    void testCheckoutWithSpecialCharacters() {
        String product = TestDataLoader.getProduct("monitor");
        CheckoutInfo weirdInfo = TestDataLoader.getCheckoutInfo("specialChars");
        // e.g., name = "<script>alert(1)</script>", city = "A".repeat(300)

        log.info("Trying checkout with special characters and long input");
        productPage.openProduct(product);
        productPage.addToCart();

        cartPage.openCart();
        cartPage.proceedToCheckout();

        checkoutPage.fillCheckoutForm(weirdInfo);
        checkoutPage.placeOrder();

        assertFalse(checkoutPage.isOrderConfirmed(), "Order should not be confirmed with invalid inputs");
        assertTrue(checkoutPage.isErrorDisplayed(), "Error should be shown for invalid input");
    }

    @Test
    @Story("Multiple Products")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that checkout works correctly with multiple products in the cart")
    void testCheckoutWithMultipleProducts() {
        String laptop = TestDataLoader.getProduct("laptop");
        String phone = TestDataLoader.getProduct("phone");
        CheckoutInfo info = TestDataLoader.getCheckoutInfo("valid");

        log.info("Adding multiple products to cart and checking out");
        productPage.openProduct(laptop);
        productPage.addToCart();

        productPage.openProduct(phone);
        productPage.addToCart();

        cartPage.openCart();
        cartPage.proceedToCheckout();

        checkoutPage.fillCheckoutForm(info);
        checkoutPage.placeOrder();

        assertTrue(checkoutPage.isOrderConfirmed(), "Order should be confirmed for multiple products");
    }


}
