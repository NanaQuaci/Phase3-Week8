package com.projects.pages;

import com.codeborne.selenide.Selenide;
import com.projects.base.BasePage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;

public class HomePage extends BasePage {

    // open homepage
    public void open() {
        Selenide.open("/");
    }

    // check homepage is loaded
    public boolean isLoaded() {
        return $("#nava").is(visible); // or any unique homepage element
    }

    // check if products are listed
    public boolean hasProducts() {
        return !$$(".card-block, .col-lg-4").isEmpty();
    }

    // click "Contact" link in navbar
    public void clickContact() {
        $$("a.nav-link").findBy(text("Contact")).click();
    }

    // check if contact modal pops up
    public boolean isContactModalVisible() {
        try {
            // Wait a bit for modal state to stabilize
            return $("#exampleModal").shouldBe(visible, Duration.ofSeconds(2)).exists();
        } catch (com.codeborne.selenide.ex.ElementShould e) {
            // If element is not visible
            return false;
        }
    }



    public void closeContactModal() {
        $("#exampleModal .close").click(); // click the "×" button
        $("#exampleModal").shouldNotBe(visible, Duration.ofSeconds(2));
    }

    // click "Home" link in navbar
    public void clickHome() {
        $$("a.nav-link").findBy(text("Home")).click();
    }

    // already implemented: product assertions
    public void shouldDisplayProducts() {
        $$(".card-block, .col-lg-4").shouldHave(sizeGreaterThan(0));
    }

    public boolean verifyFirstProductHasNamePriceThumbnail() {
        var firstProduct = $$(".col-lg-4").first();

        boolean hasName = firstProduct.$(".card-title, .hrefch").is(visible);
        boolean hasPrice = firstProduct.$("h5").is(visible);
        boolean hasImage = firstProduct.$("img").is(visible);

        return hasName && hasPrice && hasImage;
    }


    public void clickProduct(String productName) {
        $$(".hrefch").findBy(text(productName)).click();
    }
}
