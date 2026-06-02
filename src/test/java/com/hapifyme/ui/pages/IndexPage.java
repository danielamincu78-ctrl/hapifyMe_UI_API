package com.hapifyme.ui.pages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class IndexPage {

    private By postTextArea = By.id("post_text");
    private By publishButton = By.id("post_button");
    private By postBody = By.className("post_body_content");

    public void createPost(String message) {
        $(postTextArea).setValue(message);
        $(publishButton).click();
    }

    public void verifyPostVisible() {
        $(postBody).shouldBe(visible);
    }

    /*public String getLatestPostText() {
        return latestPost.getText();
    }*/
}