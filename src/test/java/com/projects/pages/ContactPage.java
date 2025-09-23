package com.projects.pages;

import com.projects.base.BasePage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class ContactPage extends BasePage {

    public void openContactForm() {
        clickNavLink("Contact");
    }

    public void fillContactForm(String name, String email, String message) {
        $("#recipient-name").setValue(name);
        $("#recipient-email").setValue(email);
        $("#message-text").setValue(message);
    }

    public void submitContactForm() {
        $$(".btn, a").findBy(text("Send message")).click();
        try {
            switchTo().alert().accept();
        } catch (Exception ignored) {}
    }

    public void shouldShowConfirmation() {
        $("div#exampleModal, .modal-body, .alert").shouldBe(visible);
    }

    public void shouldShowEmailValidationError() {
        $(".invalid-feedback, .error").shouldBe(visible);
    }
}
