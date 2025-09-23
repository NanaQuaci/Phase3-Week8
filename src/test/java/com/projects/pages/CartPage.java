package com.projects.pages;

import com.projects.base.BasePage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;

public class CartPage extends BasePage {

    public void openCart() {
        open("/cart.html");
    }

    public void shouldContainItems(int count) {
        $$("#tbodyid tr").shouldHave(size(count));
    }

    public void shouldContainProductWithQuantity(String productName, int qty) {
        $$("tr").findBy(text(productName)).shouldHave(text(String.valueOf(qty)));
    }

    public void removeProduct(String productName) {
        $$("tr").findBy(text(productName)).$("[onclick*='delete']").click();
    }

    public void shouldNotContainProduct(String productName) {
        $$("tr").filterBy(text(productName)).shouldHave(size(0));
    }
}
