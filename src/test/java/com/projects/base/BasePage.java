package com.projects.base;

import com.codeborne.selenide.Condition;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public abstract class BasePage {

    public void openPage(String relativeUrl) {
        open(relativeUrl);
    }

    public String getPageTitle() {
        return title();
    }

    public void clickNavLink(String linkText) {
        $$(".nav-link")
                .findBy(Condition.text(linkText))
                .shouldBe(Condition.visible, Duration.ofSeconds(2))
                .click();
    }

}
