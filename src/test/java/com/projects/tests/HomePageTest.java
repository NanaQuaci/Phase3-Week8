package com.projects.tests;

import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.projects.base.BaseTest;
import com.projects.pages.HomePage;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Product Store UI Tests")
@Feature("Homepage")
@ExtendWith({ScreenShooterExtension.class})
public class HomePageTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(HomePageTest.class);
    private final HomePage homepage = new HomePage();

    @Test
    @Story("Load Homepage")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify homepage loads and displays product list")
    void testHomepageLoadsWithProducts() {
        log.info("Opening homepage");
        homepage.open();

        assertTrue(homepage.isLoaded(), "Homepage should be loaded");
        assertTrue(homepage.hasProducts(), "Homepage should display products");
    }

    @Test
    @Story("Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify navigation links work correctly")
    void testNavigationLinks() {
        log.info("Opening homepage and clicking Contact link");
        homepage.open();
        homepage.clickContact();

        assertTrue(homepage.isContactModalVisible(), "Contact modal should be visible");

        log.info("Clicking Home link");
        homepage.clickHome();
        assertTrue(homepage.hasProducts(), "Products should be visible after clicking Home link");
    }
}
