Feature: Treasure hunting in the homework maze

  @treasureHunt
  Scenario: Play the guarded treasure maze
    Given a maze with the following rooms and neighbors:
      | Room     | Neighbors        |
      | Starting | B, C, D, E, F, G |
      | F        | Map              |
      | C        | J                |
      | J        | I                |
      | I        | K, L, M          |
      | L        | N, O             |
      | N        | Treasure         |
      | O        | P                |
    And a seeker Seeker with health 5.0
    And a coward Coward with health 5.0
    And Seeker is in the Starting room
    And Coward is in the Starting room
    And a guard named Guard in the Treasure room
    And a treasure is hidden in Treasure room
    And a treasure map is hidden in Map room
    And the maze has the following attributes:
      | number of food items | 15 |
    When the game is played in the created maze
    Then the game is over