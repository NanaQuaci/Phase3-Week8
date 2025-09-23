package com.projects.stepdefinitions;

import com.projects.pages.HomePage;
import com.projects.pages.ProductPage;
import io.cucumber.java.en.*;

public class HomepageSteps {

    private final HomePage homepage = new HomePage();
    private final ProductPage productPage = new ProductPage();

    @Given("the user is on the homepage")
    public void userOnHomepage() {
        homepage.openPage("/");
    }

    @Then("the homepage should display a list of products")
    public void homepageDisplaysProducts() {
        homepage.shouldDisplayProducts();
    }

    @When("the user clicks the product {string}")
    public void userClicksProduct(String productName) {
        homepage.clickProduct(productName);
    }

    @Then("the product detail page shows the product name {string}")
    public void productDetailShows(String productName) {
        productPage.verifyProductDetailPage(productName);
    }

    @Then("at least one product should show name, price and thumbnail")
    public void productShowsNamePriceThumbnail() {
        homepage.verifyFirstProductHasNamePriceThumbnail();
    }
}
