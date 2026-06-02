package com.hapifyme.ui.pages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

    // Locatori (privați, pentru encapsulare)
    private By emailInput = By.name("log_email");
    private By passwordInput = By.name("log_password");
    private By loginButton = By.name("login_button");
    private By registerLink = By.id("signup");

    // Metode de acțiune
    public void openPage() {
        open("/login_register.php"); // Folosește baseUrl din Hooks
    }

    public void enterEmail(String email) {
        $(emailInput).setValue(email);
    }

    public void enterPassword(String password) {
        $(passwordInput).setValue(password);
    }

    public void clickLogin() {
        $(loginButton).click();
    }

    public void login(String email, String password) {
        $(emailInput).setValue(email);
        $(passwordInput).setValue(password);
        $(loginButton).click();
    }
}