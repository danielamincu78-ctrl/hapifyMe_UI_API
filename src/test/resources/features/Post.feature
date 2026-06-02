@post @smoke
Feature: Postare noua

  Background:
    # Acesta este pasul de legătură.
    # Nu repetăm pașii de "Introduce user", "Apasa buton".
    Given utilizatorul este logat în aplicație

  Scenario: Adauga o postare in platforma HapifyMe
    When Userul scrie un post nou "Acesta este un test automatizat. "
    And userul apasa butonul Post
    Then ar trebui să vadă postarea in lista

  Scenario Outline: Adaugare postare multipla
    When userul introduce textul "<message>"
    And userul apasa butonul Post
    Then ar trebui să vadă postarea in lista

    Examples:
      | message |
      |   |
      | q |
      | Postare multipla |