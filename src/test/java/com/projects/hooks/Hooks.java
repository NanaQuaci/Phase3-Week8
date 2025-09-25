package com.projects.hooks;

import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Selenide.*;


public class Hooks {
    @Before
    public void setUp() {
        Configuration.baseUrl = "https://www.demoblaze.com";
//        Configuration.headless = false;
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless", "true"));
        Configuration.browserSize = null;
        // Optional: set remote WebDriver URL via env var for selenium grid
        // Configuration.remote = System.getenv("SELENIUM_REMOTE_URL");

        open("/");
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    @After
    public void tearDown() {
        closeWebDriver();
    }
}
