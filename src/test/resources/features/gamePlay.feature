Feature: Playing Complete Games
  As a game player
  I want to play a game with a variety of characters
  So that I can see how they interact with each other


  Scenario: Play the simplest game
    Given a maze with the following attributes:
      | number of rooms     | 1 |
      | number of knights   | 1 |
      | number of creatures | 1 |

    When the game is played in the created maze

    Then the game is over
    And all the adventurers or all of the creatures have died


  Scenario: Play a complicated game
    Given a maze with the following attributes:
      | number of rooms      | 7  |
      | number of food items | 20 |
      | number of knights    | 4  |
      | number of gluttons   | 3  |
      | number of cowards    | 1  |
      | number of creatures  | 5  |
      | number of demons     | 2  |
      | number of seekers    | 2  |
      | number of treasures  | 1  |


    When the game is played in the created maze

    Then the game is over
    And all the adventurers or all of the creatures have died


  Scenario: Fighting
    Given a maze with the following rooms:
      | Starting |
    Given a knight Bilbo with health 5 and a fixed die roll of 4
    And Bilbo is in the Starting room
    And a creature Goblin with health 3 and a fixed die roll of 2
    And Goblin is in the Starting room

    When Bilbo plays a turn

    Then Bilbo has health 4.45
    And Goblin has health 0.5


  Scenario: Play a game in a unidirectional, randomly-distributed maze
    Given a unidirectional, randomly-distributed maze with the following attributes:
      | number of rooms                | 11 |
      | number of connections per room | 3  |
      | number of food items           | 10 |
      | number of knights              | 3  |
      | number of gluttons             | 2  |
      | number of cowards              | 1  |
      | number of creatures            | 5  |
      | number of demons               | 2  |

    When the game is played in the created maze

    Then the game is over
    And all the adventurers or all of the creatures have died

  Scenario: Play a game in a bidirectional, sequentially-distributed maze
    Given a bidirectional, sequentially-distributed maze with the following attributes:
      | number of rooms                | 11 |
      | number of connections per room | 3  |
      | number of food items           | 10 |
      | number of knights              | 3  |
      | number of gluttons             | 2  |
      | number of cowards              | 1  |
      | number of creatures            | 5  |
      | number of demons               | 2  |

    When the game is played in the created maze

    Then the game is over
    And all the adventurers or all of the creatures have died

  Scenario: Three deaths have occured
    Given a maze with the following rooms:
      | Starting |
    And a knight Bruh with health 100 and a fixed die roll of 6
    And Bruh is in the Starting room
    And a creature Creature1 with health 1 and a fixed die roll of 1
    And Creature1 is in the Starting room
    And a creature Creature2 with health 1 and a fixed die roll of 1
    And Creature2 is in the Starting room
    And a creature Creature3 with health 1 and a fixed die roll of 1
    And Creature3 is in the Starting room

    When the game is played in the created maze

    Then the game is over
    And 3 deaths occured