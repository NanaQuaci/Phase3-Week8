package com.projects.base;

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
        $$(".nav a").findBy(text(linkText)).click();
    }
}
