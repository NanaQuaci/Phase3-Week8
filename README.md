🧪 QA Automation Framework – Phase3-Week8
📌 Project Summary

This repository contains a comprehensive QA Automation Framework created as part of the Phase 3 Week 8 training project. The framework was designed to automate web UI testing using industry best practices, including Page Object Model, Behavior-Driven Development (BDD), and CI/CD integration. The goal is to create scalable, maintainable automation that supports reliable regression testing and enhances release confidence.

🎯 Project Objectives

- Create a robust UI test automation framework using Java and Selenium

- Structure test code for maintainability and readability

- Implement BDD (Cucumber) for business-friendly test scenarios

- Integrate automated tests into CI/CD pipelines

- Produce clear and actionable test reporting

🛠 Tools & Technologies

The framework utilises the following technologies:

Category	Tools / Frameworks
Automation	Selenium WebDriver (Java)
Design Pattern	Page Object Model (POM)
Test Management	Cucumber (BDD), TestNG
CI/CD	GitHub Actions, Jenkins
Containerisation	Docker
Reporting	Allure Reports
Version Control	Git & GitHub
🧩 Project Structure (Simplified)
src/
 ├── main/
 |     └── java/
 |         └── pageObjects/        # Page Object classes
 └── test/
       ├── java/
       |    └── stepDefinitions/    # Cucumber step definitions
       ├── resources/
       |    └── features/           # Gherkin feature files
       └── testng.xml               # TestNG configuration

🚀 Framework Highlights
✅ Page Object Model (POM)

Separates UI locators and test logic

Improves maintainability and scalability

Enhances readability and reusability of test components

✅ Behavior-Driven Development (BDD)

Feature definitions written in human-friendly Gherkin syntax

Promotes clear communication between testers and non-technical stakeholders

Keeps test scenarios easy to visualise and understand

✅ CI/CD Integration

Automated tests are integrated into CI/CD pipelines using:

GitHub Actions

Jenkins

Docker containers

This enables:

Automatic test execution on push/pull requests

Faster feedback on code quality

Easier adoption in agile development environments

🧪 Test Reporting

Allure Reports are generated to provide:

High-level summary of test results

Step-by-step test breakdown

Detailed test execution logs

Visual charts for passed/failed tests

This supports stakeholder visibility and better release decisions.

📈 Value Delivered

Reduced manual regression effort

Standardised automation framework for future tests

Improved visibility into test outcomes

Faster integration with CI/CD workflows

Readily reusable automation structure for new features

🧠 How to Run the Tests

Clone this repository

git clone https://github.com/NanaQuaci/Phase3-Week8.git


Navigate to the project root and install dependencies

mvn clean install


Run tests locally

mvn test


View generated Allure HTML report

allure serve target/allure-results


Note: Ensure Java, Maven, Docker, and Allure are installed on your system.

👨‍💻 Author

Collins Kwasi Adu

Quality Assurance Engineer

📧 aducollins49@gmail.com

🔗 LinkedIn: https://www.linkedin.com/in/collins-adu-20a5a8217/

🔗 GitHub: https://github.com/NanaQuaci

🔗 Find Test Reports: https://nanaquaci.github.io/Phase3-Week8/
