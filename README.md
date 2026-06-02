# [![hapifyMe Automated Tests](https://github.com/danielamincu78-ctrl/hapifyMe_UI_API/actions/workflows/main.yml/badge.svg)](https://github.com/danielamincu78-ctrl/hapifyMe_UI_API/actions/workflows/main.yml)

# HapifyMe Automation Framework

Automation framework developed for testing the HapifyMe application using both API and UI automated tests.

The project combines:

* API Testing with REST Assured
* UI Testing with Selenide
* BDD Scenarios with Cucumber
* JUnit 4 for UI test execution
* TestNG for API test execution
* Maven for dependency management and build automation
* Allure Reports for test reporting
* GitHub Actions for Continuous Integration

---

## Tech Stack

### API Testing

* Java 21
* REST Assured
* TestNG
* Jackson Databind
* Log4j2
* Awaitility

### UI Testing

* Selenide
* Selenium WebDriver
* Cucumber
* JUnit 4

### Reporting & CI/CD

* Allure Reports
* GitHub Actions
* GitHub Pages

---

## Project Structure

```text
src
├── test
│   ├── java
│   │   └── com.hapifyme
│   │       ├── api
│   │       │   ├── models
│   │       │   ├── tests
│   │       │   └── utils
│   │       └── ui
│   │           ├── pages
│   │           ├── runners
│   │           └── stepdefinitions
│   │
│   └── resources
│       ├── features
        ├── allure.properties
│       └── log4j2.xml
│
└── pom.xml
```

---

## Automated API Scenarios

### Full User Lifecycle

The API automation validates the complete lifecycle of a user account:

1. Register User
2. Confirm Email
3. Login
4. Get Profile
5. Update Profile
6. Delete Profile
7. Negative Validation (User Not Found)

---

## Automated UI Scenarios

### Login

* Successful login
* Invalid login

### Search

* Search existing users

### Posts

* Create new post
* Validate invalid post scenarios

### Profile

* Update profile information

---

## Running Tests Locally

### Execute all tests

```bash
mvn clean test
```

### Execute only API tests

```bash
mvn test -Dtest=FullUserLifecycleTest
```

### Execute only UI tests

Run the Cucumber runner:

```text
src/test/java/com/hapifyme/ui/runners/TestRunner.java
```

or

```bash
mvn test -Dcucumber.filter.tags="@smoke"
```

---

## Test Reports

### Allure Report

Generate report:

```bash
allure generate target/allure-results --clean
```

Open report:

```bash
allure open allure-report
```

or

```bash
allure serve target/allure-results
```
---

## Continuous Integration

The project uses GitHub Actions to:

* Build the project
* Execute API and UI automated tests
* Generate Allure reports
* Publish reports to GitHub Pages

---

## Author
Daniela Mincu
QA Automation Engineer Portfolio Project
