package com.hapifyme.ui.pages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {
    private By settingsButton = By.xpath("/html[1]/body[1]/div[1]/nav[1]/a[6]/i[1]");
    private By firstNameInput = By.name("first_name");
    private By lastNameInput = By.name("last_name");
    private By emailInput = By.name("email");
    private By updateButton = By.id("save_details");
    private By successMessage = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > form:nth-child(9)");

    public void openSettings() {
        $(settingsButton).click();
    }
    public void setFirstName(String firstName) {
        $(firstNameInput).setValue(firstName);
    }

    public void setLastName(String lastName) {
        $(lastNameInput).setValue(lastName);
    }

    public void setEmail(String email) {
        $(emailInput).setValue(email);
    }

    public void clickUpdate() {
        $(updateButton).click();
    }

    public void verifySuccessMessage() {
        $(successMessage).shouldBe(visible);
        $(successMessage).shouldHave(text("Details updated!"));
    }
}




