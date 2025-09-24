package com.projects.pages;

import com.projects.base.BasePage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;

public class CartPage extends BasePage {

    public void openCart() {
        open("/cart.html");
    }

    public void proceedToCheckout() {
        $$(".btn, button, a").findBy(text("Checkout"))
                .shouldBe(visible)
                .click();
    }

    public boolean containsProduct(String productName) {
        return $$("tr").findBy(text(productName)).exists();
    }

    public int getQuantityFor(String productName) {
        return Integer.parseInt(
                $$("tr").findBy(text(productName)).$("td:nth-child(3)").getText()
        );
    }

    public void removeItem(String productName) {
        $$("tr").findBy(text(productName)).$("[onclick*='delete']").click();
    }

    public void shouldContainItems(int count) {
        $$("#tbodyid tr").shouldHave(size(count));
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
        refresh(); // reload page to reflect latest cart state
    }
}
