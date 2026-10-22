Feature: Details of Character Action
  As a game player
  I want to see how various characters react to different situations
  So that I can see how they interact with each other

  Scenario: A Knight moves if nothing is in the current room
    Given a fully-connected maze with the following rooms:
      | Starting |
      | Final    |
    And a knight named Galahad in the Starting room

    When Galahad plays a turn

    Then Final room contains Galahad
    And Starting room does not contain Galahad

  Scenario: A Knight will fight a Creature even if he can run away
    Given a fully-connected maze with the following rooms:
      | Starting  |
      | OtherRoom |
    And a knight named Galahad in the Starting room
    And a creature named Ogre in the Starting room

    When Galahad plays a turn

    Then Ogre lost some health
    And a fight occured

  Scenario: A Knight will eat food if no creature is in the room
    Given a fully-connected maze with the following rooms:
      | Starting  |
      | OtherRoom |
    And the Starting room contains the following food items:
      | Name    | Value |
      | cupcake | 1.0   |
    And a knight named Galahad in the Starting room

    When Galahad plays a turn

    Then the Starting room no longer contains cupcake
    And Galahad is in the Starting room
    # Gained 1 from the food, but lost 0.05 for the turn
    And Galahad has gained 0.95 in health

  Scenario: Behavior of a Glutton
    Given a fully-connected maze with the following rooms:
      | Starting |
      | Final    |
    And a glutton named Tiny in the Starting room
    And the Starting room contains the following food items:
      | Name    | Value |
      | cupcake | 1.0   |

    When Tiny plays a turn

    Then the Starting room no longer contains cupcake
    And Tiny has gained 0.95 in health

  Scenario: Glutton will never fight a Creature even if he cannot run away
    Given a fully-connected maze with the following rooms:
      | Only |
    And a glutton EatsALot with health 3.0
    And EatsALot is in the Only room

    And a creature named Ogre in the Only room

    When EatsALot plays a turn

    Then EatsALot has health 2.95


  Scenario: Coward will run away if it can
    Given a maze with the following rooms:
      | Starting |
      | Other    |
      | Third    |
      | Fourth   |
    And each room is connected to 2 other rooms via 1-way connections
    And a coward named RunAway in the Starting room
    And a creature named Ogre in the Starting room
    And Ogre is in the Starting room
    And food Popcorn is in the Starting room

    When RunAway plays a turn

    Then Ogre did not lose some health
    And RunAway lost some health
    And RunAway is in the Other room


  Scenario: Glutton will eat food if it is available over fighting or running
    Given a fully-connected maze with the following rooms:
      | Starting  |
      | OtherRoom |
    And the Starting room contains the following food items:
      | Name  | Value |
      | apple | 1.0   |
    And a glutton named Eats-a-lot in the Starting room
    And a creature named Ogre in the Starting room

    When Eats-a-lot plays a turn

    Then Eats-a-lot is in the Starting room
    And the Starting room has 0 food items
    And Eats-a-lot has gained 0.95 in health
    And Ogre did not lose some health


  Scenario: A Demon will fight a Glutton even when food and neighboring rooms are available
    Given a fully-connected maze with the following rooms:
      | Starting  |
      | OtherRoom |
    And a glutton EatsALot with health 3.0 and a fixed die roll of 3
    And EatsALot is in the Starting room
    And a demon Satan with health 10.0 and a fixed die roll of 5
    And Satan is in the Starting room
    And the Starting room contains the following food items:
      | Name  | Value |
      | apple | 1.0   |

    When Satan plays a turn

    Then a fight occured
    And EatsALot has health 0.5
    And Satan has health 9.45