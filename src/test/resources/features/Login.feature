@login @smoke
Feature: Autentificarea în HapifyMe

  Background:
    Given utilizatorul deschide pagina de login "https://apps.qualiadept.eu/hapifyme/login_register.php"

  Scenario: Login cu succes folosind credențiale valide
    When utilizatorul introduce emailul "test.automat@qaschool.ro"
    And utilizatorul introduce parola "Password@123"
    And utilizatorul apasă butonul de login
    Then utilizatorul ar trebui să fie redirecționat către homepage

  Scenario Outline: Login eșuat cu date invalide
    When utilizatorul introduce emailul "<email>"
    And utilizatorul introduce parola "<password>"
    And utilizatorul apasă butonul de login
    Then utilizatorul ar trebui să vadă un mesaj de eroare "<error_message>"

    Examples:
      | email                    | password       | error_message               |
      | user.inexistent@test.com | parola123      | Email or password was incorrect |
      | dana_mincu@yahoo.com     | gresita123     | Email or password was incorrect |



