package com.projects.tests;

import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.projects.base.BaseTest;
import com.projects.pages.ContactPage;
import com.projects.util.ContactInfo;
import com.projects.util.TestDataLoader;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Product Store UI Tests")
@Feature("Contact Form")
@ExtendWith({ScreenShooterExtension.class})
public class ContactTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(ContactTest.class);
    private final ContactPage contactPage = new ContactPage();

    @Test
    @Story("Valid Submission")
    @Severity(SeverityLevel.CRITICAL)
    void testValidContactSubmission() {
        ContactInfo info = TestDataLoader.getContactInfo("valid");

        log.info("Submitting valid contact form");
        contactPage.openContactForm();
        contactPage.fillContactForm(info);
        contactPage.submitContactForm();

        assertTrue(contactPage.shouldShowConfirmation(), "Success alert should appear after valid submission");
    }

    @Test
    @Story("Invalid Email")
    @Severity(SeverityLevel.NORMAL)
    void testInvalidEmailContactSubmission() {
        ContactInfo info = TestDataLoader.getContactInfo("invalid");

        log.info("Submitting contact form with invalid email");
        contactPage.openContactForm();
        contactPage.fillContactForm(info);
        contactPage.submitContactForm();

        assertTrue(contactPage.shouldShowEmailValidationError(), "Error alert should appear for invalid email");
    }

    @Test
    @Story("Empty Fields")
    @Severity(SeverityLevel.CRITICAL)
    void testEmptyContactFormSubmission() {
        ContactInfo info = TestDataLoader.getContactInfo("empty");

        log.info("Submitting contact form with all fields empty");
        contactPage.openContactForm();
        contactPage.fillContactForm(info);
        contactPage.submitContactForm();

        assertTrue(contactPage.shouldShowEmailValidationError() || !contactPage.shouldShowConfirmation(),
                "Form submission with empty fields should trigger validation error");
    }

    @Test
    @Story("Empty Email")
    @Severity(SeverityLevel.NORMAL)
    void testEmptyEmailContactSubmission() {
        ContactInfo info = TestDataLoader.getContactInfo("emptyEmail");

        log.info("Submitting contact form with empty email");
        contactPage.openContactForm();
        contactPage.fillContactForm(info);
        contactPage.submitContactForm();

        assertTrue(contactPage.shouldShowEmailValidationError(),
                "Form submission with empty email should trigger validation error");
    }

    @Test
    @Story("Empty Message")
    @Severity(SeverityLevel.MINOR)
    void testEmptyMessageContactSubmission() {
        ContactInfo info = TestDataLoader.getContactInfo("emptyMessage");

        log.info("Submitting contact form with empty message");
        contactPage.openContactForm();
        contactPage.fillContactForm(info);
        contactPage.submitContactForm();

        // Depending on how Demoblaze behaves, this might still pass — so you might check alert text here if needed
        assertFalse(contactPage.shouldShowConfirmation(),
                "Form submission with empty message should not be treated as a valid success");
    }

}