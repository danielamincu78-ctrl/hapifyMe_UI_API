package com.hapifyme.ui.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        // Calea către fișierele feature (folderul resources)
        features = "src/test/resources/features",

        // Pachetul unde se află clasele Java cu @Given, @When (glue code)
        glue = "com.hapifyme.ui.stepdefinitions",

        // tags = "@smoke or @regression", // Rulează testele care au ORICARE din aceste tag-uri
        tags = "@smoke",

        // Plugin-uri pentru rapoarte (în consolă și HTML)
        plugin = {"pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },

        // Face output-ul din consolă mai ușor de citit
        monochrome = true
)
public class CucumberTest {
    // Această clasă rămâne goală!
    // Ea servește doar ca suport pentru adnotări.
}