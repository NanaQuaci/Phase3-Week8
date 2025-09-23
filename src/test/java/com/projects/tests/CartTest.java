package com.projects.tests;

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
        assertEquals(2, cartPage.getQuantityFor(product),
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
    @Story("Cart Persistence")
    @Severity(SeverityLevel.NORMAL)
    void testCartPersistenceAfterReload() {
        String product = TestDataLoader.getProduct("monitor");

        log.info("Adding '{}' and refreshing cart", product);
        productPage.openProduct(product);
        productPage.addToCart();
        cartPage.openCart();

        assertTrue(cartPage.containsProduct(product), "Cart should contain item before reload");
        cartPage.refresh();
        assertTrue(cartPage.containsProduct(product), "Cart should still contain item after reload");
    }
}
