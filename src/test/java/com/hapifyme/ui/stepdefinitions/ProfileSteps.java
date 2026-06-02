package com.hapifyme.ui.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.hapifyme.ui.pages.ProfilePage;


public class ProfileSteps {
    ProfilePage profilePage = new ProfilePage();
    @When("utilizatorul apasa butonul Settings")
    public void accessSettings() {
        profilePage.openSettings();
    }

    @When("utilizatorul introduce numele {string}")
    public void inputFirstName(String firstName) {
        profilePage.setFirstName(firstName);
    }

    @When("utilizatorul introduce prenumele {string}")
    public void inputLastName(String lastName) {
        profilePage.setLastName(lastName);
    }

    @When("utilizatorul introduce emailul profilului {string}")
    public void inputEmail(String email) {
        profilePage.setEmail(email);
    }

    @When("utilizatorul apasa butonul Update Details")
    public void updateButton() {
        profilePage.clickUpdate();
    }

    @Then("poate edita profilul")
    public void editProfile() {
        profilePage.verifySuccessMessage();
    }
}

