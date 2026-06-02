package com.hapifyme.ui.stepdefinitions;

import com.codeborne.selenide.Configuration;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import static com.codeborne.selenide.Selenide.*;

public class Hooks {

    @Before
    public void setup() {
        // Configurare globală Selenide
        Configuration.browser = "chrome";
        Configuration.baseUrl = "https://apps.qualiadept.eu/hapifyme";
        Configuration.browserSize = "1920x1080";

        System.out.println("--- Start Scenariu ---");
    }

    @After
    public void tearDown(Scenario scenario) {
        // Facem screenshot doar dacă testul a eșuat
        if (scenario.isFailed()) {
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            screenshot(screenshotName); // Funcție Selenide
            System.out.println("!!! Scenariu Eșuat. Screenshot salvat.");
        }

        // Închidem browserul pentru a avea un mediu curat la următorul test
        closeWebDriver();
        System.out.println("--- Stop Scenariu ---");
    }
}