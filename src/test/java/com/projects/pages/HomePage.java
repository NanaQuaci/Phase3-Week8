package com.projects.pages;

import com.projects.base.BasePage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;

public class HomePage extends BasePage {

    public void shouldDisplayProducts() {
        $$(".card-block, .col-lg-4").shouldHave(sizeGreaterThan(0));
    }

    public void verifyFirstProductHasNamePriceThumbnail() {
        var firstProduct = $$(".card-block, .col-lg-4").first();
        firstProduct.$(".card-title, .hrefch").shouldBe(visible);
        firstProduct.$(".card-price, .price-container").shouldBe(visible);
        firstProduct.$("img").shouldBe(visible);
    }

    public void clickProduct(String productName) {
        $$(".hrefch").findBy(text(productName)).click();
    }
}
