package com.hapifyme.ui.stepdefinitions;

import io.cucumber.java.en.*;
import com.hapifyme.ui.pages.LoginPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class LoginSteps {
    LoginPage loginPage = new LoginPage();

    @Given("utilizatorul deschide pagina de login {string}")
    public void openLoginPage(String url) {
        open(url);
    }

    @When("utilizatorul introduce emailul {string}")
    public void enterEmail(String email) {
        loginPage.enterEmail(email);
    }

    @And("utilizatorul introduce parola {string}")
    public void enterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @And("utilizatorul apasă butonul de login")
    public void clickLogin() {
        loginPage.clickLogin();
    }

    @Then("utilizatorul ar trebui să fie redirecționat către homepage")
    public void verifyHomepage() {
        // Verificăm un element care există doar în index.php (după login)
        $(".posts_area").shouldBe(visible);
    }

    @Then("utilizatorul ar trebui să vadă un mesaj de eroare {string}")
    public void verifyErrorMessage(String expectedError) {
        // Verificăm dacă textul așteptat apare în body (HapifyMe afișează erorile ca text simplu uneori)
        $("body").shouldHave(text(expectedError));
    }

}
