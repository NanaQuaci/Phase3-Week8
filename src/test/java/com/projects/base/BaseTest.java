package com.projects.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.*;

public abstract class BaseTest {
    @BeforeEach
    void setUp() {
        Configuration.baseUrl = "https://www.demoblaze.com";
        Configuration.headless = false;
        Configuration.browserSize = null;

        open("/");
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}
