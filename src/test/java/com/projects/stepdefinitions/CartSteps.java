package com.projects.stepdefinitions;

import com.projects.pages.CartPage;
import com.projects.pages.HomePage;
import com.projects.pages.ProductPage;
import io.cucumber.java.en.*;

public class CartSteps {

    private final HomePage homepage = new HomePage();
    private final ProductPage productPage = new ProductPage();
    private final CartPage cartPage = new CartPage();

    @Given("the user is on the product detail page for {string}")
    public void userOnProductDetail(String productName) {
        homepage.openPage("/");
        homepage.clickProduct(productName);
    }

    @When("the user clicks {string}")
    public void userClicksAddToCart(String buttonLabel) {
        productPage.clickAddToCart(buttonLabel);
    }

    @Then("a confirmation should be shown")
    public void confirmationShown() {
        productPage.shouldShowAddToCartConfirmation();
    }

    @Given("the user has added {string} to the cart")
    public void addProductToCart(String productName) {
        userOnProductDetail(productName);
        userClicksAddToCart("Add to cart");
    }

    @When("the user goes to the cart page")
    public void userGoesToCartPage() {
        cartPage.openCart();
    }

    @Then("the quantity for {string} should be {int}")
    public void cartShowsQuantity(String productName, Integer qty) {
        cartPage.shouldContainProductWithQuantity(productName, qty);
    }

    @When("the user removes {string}")
    public void userRemovesProduct(String productName) {
        cartPage.removeProduct(productName);
    }

    @Then("the cart should not contain {string}")
    public void cartNotContain(String productName) {
        cartPage.shouldNotContainProduct(productName);
    }

    @Then("the cart should contain {int} item(s)")
    public void cartShouldContainItems(Integer count) {
        cartPage.shouldContainItems(count);
    }
}
