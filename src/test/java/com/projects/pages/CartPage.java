package com.projects.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.projects.base.BasePage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Selenide.refresh;

public class CartPage extends BasePage {

    public void openCart() {
        open("/cart.html");
    }

    public void proceedToCheckout() {
        $$(".btn, button, a").findBy(Condition.text("Place Order"))
                .shouldBe(Condition.visible, Duration.ofSeconds(2))
                .click();
    }

    public boolean containsProduct(String productName) {
        return $$("tr").findBy(text(productName)).exists();
    }

    public int getEntriesFor(String productName) {
        return $$("tr").filterBy(text(productName))
                .shouldBe(sizeGreaterThan(0))   // wait until at least 1 entry is there
                .size();
    }


    public void removeItem(String productName) {
        var row = $$("tr").findBy(text(productName));
        if (row.exists()) {
            row.$("[onclick*='delete']").click();
        }
    }


    public void shouldContainItems(int count) {
        $$("#tbodyid tr").shouldHave(size(count));
    }

    public void waitUntilProductVisible(String productName) {
        $$("#tbodyid tr").findBy(text(productName)).shouldBe(visible);
    }


    public void shouldContainProductWithQuantity(String productName, int qty) {
        $$("tr").findBy(text(productName)).shouldHave(text(String.valueOf(qty)));
    }

    public void shouldHaveNumberOfEntries(String productName, int expectedCount) {
        $$("tr").filterBy(text(productName)).shouldHave(size(expectedCount));
    }


    public void shouldNotContainProduct(String productName) {
        $$("tr").filterBy(text(productName)).shouldHave(size(0));
    }

    public void refresh() {
        Selenide.refresh();
    }
}
