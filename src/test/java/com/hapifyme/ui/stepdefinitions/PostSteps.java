package com.hapifyme.ui.stepdefinitions;

import io.cucumber.java.en.*;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import com.hapifyme.ui.pages.IndexPage;

import static com.codeborne.selenide.Selenide.*;

public class PostSteps {
    IndexPage indexPage = new IndexPage();

    @When("Userul scrie un post nou {string}")
    public void postAdd(String message) {
        indexPage.createPost("Acesta este un test automatizat");
    }

    @When("userul introduce textul {string}")
    public void postMultiple(String message) {
        indexPage.createPost(message);
    }

    @And("userul apasa butonul Post")
    public void clickPost() {
        $(By.id("post_button")).click();
    }

    @Then("ar trebui să vadă postarea in lista")
    public void verifyResults() {
        indexPage.verifyPostVisible();
    }
}