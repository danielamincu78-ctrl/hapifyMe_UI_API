@updateprofile @smoke
Feature: Editare profil

  Background:
    # Acesta este pasul de legătură.
    # Nu repetăm pașii de "Introduce user", "Apasa buton".
    Given utilizatorul este logat în aplicație

  Scenario: Utilizatorul editeaza profilul
    When utilizatorul apasa butonul Settings
    And utilizatorul introduce numele "test02"
    And utilizatorul introduce prenumele "automat"
    And utilizatorul introduce emailul profilului "test.automat@qaschool.ro"
    And utilizatorul apasa butonul Update Details
    Then poate edita profilul

