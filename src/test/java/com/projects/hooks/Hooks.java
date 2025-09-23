package com.projects.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Selenide.*;


public class Hooks {
    @Before
    public void setUp() {
        Configuration.baseUrl = "https://www.demoblaze.com";
        Configuration.headless = false;         // CI-friendly; turn off for local debugging
        Configuration.browserSize = "1920x1080";
        // Optional: set remote WebDriver URL via env var for selenium grid
        // Configuration.remote = System.getenv("SELENIUM_REMOTE_URL");
    }

    @After
    public void tearDown() {
        closeWebDriver();
    }
}
