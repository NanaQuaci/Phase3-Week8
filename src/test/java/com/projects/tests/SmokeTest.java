package com.projects.tests;

import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.projects.base.BaseTest;
import com.projects.pages.HomePage;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Product Store UI Tests")
@Feature("Smoke")
@ExtendWith({ScreenShooterExtension.class})
public class SmokeTest extends BaseTest {

    private final HomePage homepage = new HomePage();

    @Test
    @Story("Homepage Smoke Check")
    @Severity(SeverityLevel.BLOCKER)
    void testHomepageLoads() {
        homepage.open();
        assertTrue(homepage.hasProducts(), "Smoke test: homepage should display at least one product");
    }
}
