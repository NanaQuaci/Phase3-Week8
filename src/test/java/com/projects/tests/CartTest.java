package com.projects.tests;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.projects.base.BaseTest;
import com.projects.pages.CartPage;
import com.projects.pages.ProductPage;
import com.projects.util.TestDataLoader;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.codeborne.selenide.Selenide.sleep;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Product Store UI Tests")
@Feature("Cart")
@ExtendWith({ScreenShooterExtension.class})
public class CartTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(CartTest.class);
    private final ProductPage productPage = new ProductPage();
    private final CartPage cartPage = new CartPage();

    @Test
    @Story("Add Single Item")
    @Severity(SeverityLevel.CRITICAL)
    void testAddSingleItem() {
        String product = TestDataLoader.getProduct("laptop");

        log.info("Adding '{}' to cart", product);
        productPage.openProduct(product);
        productPage.addToCart();

        cartPage.openCart();
        cartPage.waitUntilProductVisible(product);
        assertTrue(cartPage.containsProduct(product), "Cart should contain the added product");
    }

    @Test
    @Story("Add Same Item Twice")
    @Severity(SeverityLevel.NORMAL)
    void testAddSameItemTwice() {
        String product = TestDataLoader.getProduct("laptop");

        log.info("Adding '{}' twice", product);
        productPage.openProduct(product);
        productPage.addToCart();
        productPage.addToCart();

        cartPage.openCart();
        int actualCount = cartPage.getEntriesFor(product);

        assertEquals(2, actualCount,
                "Quantity should be 2 when the same product is added twice");
    }

    @Test
    @Story("Remove Nonexistent Item")
    @Severity(SeverityLevel.MINOR)
    void testRemoveNonexistentItem() {
        String product = TestDataLoader.getProduct("phone");

        log.info("Trying to remove product '{}' not in cart", product);
        cartPage.openCart();
        cartPage.removeItem(product);

        assertFalse(cartPage.containsProduct(product), "Nonexistent product should not appear in cart");
    }

    @Test
    @Story("Remove Item")
    @Severity(SeverityLevel.CRITICAL)
    void testRemoveItem() {
        String product = TestDataLoader.getProduct("phone");

        log.info("Adding and then removing product '{}'", product);
        productPage.openProduct(product);
        productPage.addToCart();

        cartPage.openCart();
        cartPage.waitUntilProductVisible(product);
        cartPage.removeItem(product);

        cartPage.shouldNotContainProduct(product);
    }


    @Test
    @Story("Cart Persistence")
    @Severity(SeverityLevel.NORMAL)
    void testCartPersistenceAfterReload() {
        String product = TestDataLoader.getProduct("phone");

        log.info("Adding '{}' and refreshing cart", product);
        productPage.openProduct(product);
        productPage.addToCart();
        cartPage.openCart();

        assertTrue(cartPage.containsProduct(product), "Cart should contain item before reload");
        cartPage.refresh();
        assertTrue(cartPage.containsProduct(product), "Cart should still contain item after reload");
    }

    @Test
    @Story("Add Multiple Different Items")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that adding different items shows all of them in the cart")
    void testAddMultipleDifferentItems() {
        String laptop = TestDataLoader.getProduct("laptop");
        String phone = TestDataLoader.getProduct("phone");

        log.info("Adding '{}' and '{}'", laptop, phone);
        productPage.openProduct(laptop);
        productPage.addToCart();
        productPage.openProduct(phone);
        productPage.addToCart();

        cartPage.openCart();

        assertTrue(cartPage.containsProduct(laptop), "Cart should contain the laptop");
        assertTrue(cartPage.containsProduct(phone), "Cart should contain the phone");
        cartPage.shouldContainItems(2);
    }


    @Test
    @Story("Empty Cart State")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that cart shows empty state after removing all items")
    void testEmptyCartAfterRemovingAllItems() {
        String product = TestDataLoader.getProduct("laptop");

        log.info("Adding and removing all products to verify empty cart state");
        productPage.openProduct(product);
        productPage.addToCart();

        cartPage.openCart();
        cartPage.removeItem(product);

        cartPage.shouldNotContainProduct(product);
        assertTrue(cartPage.isEmptyCartMessageVisible(), "Cart should display empty state after removing all items");
    }


    @Test
    @Story("Cart Reset on New Session")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify cart does not persist between browser sessions (if expected)")
    void testCartIsClearedOnNewSession() {
        String product = TestDataLoader.getProduct("laptop");

        log.info("Adding product to cart and closing browser to simulate new session");
        productPage.openProduct(product);
        productPage.addToCart();
        cartPage.openCart();
        assertTrue(cartPage.containsProduct(product));

        WebDriverRunner.closeWebDriver();  // end session

        // Start new session
        cartPage.openCart();
        assertTrue(cartPage.isEmptyCartMessageVisible(), "Cart should be empty in a new session");
    }


    @Test
    @Story("Rapid Add-Remove")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify no cart inconsistency occurs when adding and removing quickly")
    void testRapidAddRemove() {
        String product = TestDataLoader.getProduct("phone");

        log.info("Rapidly adding and removing '{}'", product);
        productPage.openProduct(product);
        productPage.addToCart();
        cartPage.openCart();
        cartPage.removeItem(product);

        productPage.openProduct(product);
        productPage.addToCart();
        cartPage.openCart();

        assertTrue(cartPage.containsProduct(product), "Product should be present after re-adding");
    }


}
