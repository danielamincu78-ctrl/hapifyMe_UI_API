package com.hapifyme.ui.stepdefinitions;

import io.cucumber.java.en.Given;
import com.hapifyme.ui.pages.LoginPage;

public class CommonSteps {

    // 1. Instanțiem Page Object-ul
    LoginPage loginPage = new LoginPage();

    // 2. Mapăm textul din Gherkin (Pasul B)
    @Given("utilizatorul este logat în aplicație")
    public void performBackgroundLogin() {
        System.out.println("--- Background: Se execută Login automat ---");

        // 3. Reutilizăm logica de business din Page Object
        loginPage.openPage();

        // Folosim un cont de test valid (hardcodat sau din config)
        loginPage.login("test.automat@qaschool.ro", "Password@123");

        System.out.println("--- Background: Login complet. Începe testul de Update profile ---");
    }
}
