Feature: How the strategy of various characters change
  As a game player
  I want to see how various characters change strategy based on past events
  So that I can see how they interact with each other

  Scenario: A knight will become a coward once his health drops below Coward Threshold
    Given a maze with the following rooms and neighbors:
      | Room     | Neighbors |
      | Starting | Other     |
      | Other    | Starting  |

    And a knight Bilbo with health 6 and a fixed die roll of 2
    And Bilbo is in the Starting room
    And a creature Goblin with health 3 and a fixed die roll of 5
    And Goblin is in the Starting room
    And the Coward Threshold is 3

    # Here Bilbo plays like a knight, fighting creatures if he can
    When Bilbo plays a turn

    # Bilbo will have lost mandatory fight points of 0.5, but the dice difference is 3, so he will have lost 3.5 health points
    Then Bilbo has health 2.45
    And a fight occured

    # Here Bilbo plays like a coward, running away
    When Bilbo plays a turn

    Then Bilbo is in the Other room


  Scenario: A glutton will become a fighter once they have killed a creature
    Given a maze with the following rooms and neighbors:
      | Room     | Neighbors |
      | Starting | Other     |
      | Other    | Starting  |

    And a glutton Bilbo with health 5 and a fixed die roll of 5
    And Bilbo is in the Starting room
    And a creature Goblin with health 2 and a fixed die roll of 2
    And Goblin is in the Starting room

    # Here Goblin fights Bilbo, and Bilbo will have killed Goblin in the first turn
    When Goblin plays a turn

    Then Goblin is now dead
    # 0.5 for mandatory health points lost for fighting
    Then a fight occured
    And Bilbo has health 4.5

    Given a creature Orc with health 4 and a fixed die roll of 3
    And Orc is in the Starting room
    And the Starting room contains the following food items:
      | Name    | Value |
      | cupcake | 1.0   |

    # Now Bilbo plays like a fighter, fighting creatures even when food and other rooms are available
    When Bilbo plays a turn
    Then Bilbo is in the Starting room
    # Bilbo doesn't eat food, but fights instead
    And a fight occured
    And Bilbo has health 3.95
    And Orc has health 1.5


  Scenario: A coward will become a seeker once they have picked up a map
    Given a maze with the following rooms and neighbors:
      | Room     | Neighbors |
      | Starting | Other     |
      | Other    | Starting  |

    And the Starting room contains the following artifacts items:
      | Type     | Name  | Value |
      | Treasure | Chest | 0.0   |

    And the Other room contains the following artifacts items:
      | Type         | Name       | Value |
      | Food         | cupcake    | 1.0   |
      | Treasure Map | MapToChest | 0.0   |

    And a coward Bilbo with health 5 and a fixed die roll of 5
    And Bilbo is in the Starting room
    And a creature Goblin with health 2 and a fixed die roll of 2
    And Goblin is in the Starting room

    When Bilbo plays a turn
    # Bilbo runs from creature and ignores treasure
    Then Bilbo is in the Other room

    When Bilbo plays a turn
    Then the Other room no longer contains Treasure Map

    When Bilbo plays a turn
    Then Bilbo is in the Starting room

    When Bilbo plays a turn
    And Goblin is now dead

    When Bilbo plays a turn
    Then the Other room no longer contains Chest
    And Bilbo has found the treasure
    And the game is over