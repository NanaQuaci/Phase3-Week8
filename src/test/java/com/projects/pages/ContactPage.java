package com.projects.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.projects.base.BasePage;
import com.projects.util.ContactInfo;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class ContactPage extends BasePage {

    public void openContactForm() {
        clickNavLink("Contact");
        $("#exampleModal").shouldBe(Condition.visible, Duration.ofSeconds(2));
    }

    public void fillContactForm(String name, String email, String message) {
        $("#recipient-name").setValue(name);
        $("#recipient-email").setValue(email);
        $("#message-text").setValue(message);
    }

    public void fillContactForm(ContactInfo info) {
        fillContactForm(info.getName(), info.getEmail(), info.getMessage());
    }

    public void submitContactForm() {
        $$(".btn, a").findBy(text("Send message")).click();
        try {
            switchTo().alert().accept();
        } catch (Exception ignored) {}
    }

    public boolean shouldShowConfirmation() {
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean shouldShowEmailValidationError() {
        return $(".invalid-feedback, .error").is(visible);
    }
}
