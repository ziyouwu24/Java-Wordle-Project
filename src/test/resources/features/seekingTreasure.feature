Feature: Behavior of treasure-seeking character
  As a game player
  I want to see a character methodically search a maze for the treasure
  So that I can see that the treasure will eventually be found

  Scenario: A Seeker will always enter a room it hasn't visited given a choice
    Given a maze with the following rooms and neighbors:
      | Room     | Neighbors |
      | Starting | Next1     |
      | Next1    | Next2     |
      | Next2    | Next3     |
      | Next3    | Finish    |
    And a seeker named Bilbo in the Starting room

    When Bilbo plays a turn
    Then Bilbo is in the Next1 room

    When Bilbo plays a turn
    Then Bilbo is in the Next2 room

    When Bilbo plays a turn
    Then Bilbo is in the Next3 room

    When Bilbo plays a turn
    Then Bilbo is in the Finish room


  Scenario: A Seeker will find the treasure in a directed way, not a random way
    Given a maze with the following rooms and neighbors:
    # A bidirectional line of three rooms
      | Room         | Neighbors          |
      | Starting     | Intermediate       |
      | Intermediate | Starting, Treasure |
      | Treasure     | Intermediate       |
    And a seeker Bilbo with health 5.0 and a fixed die roll of 6
    And Bilbo is in the Starting room
    And a creature named Orc in the Intermediate room
    And a guard BlackKnight with health 2.0 and a fixed die roll of 1
    And BlackKnight is in the Treasure room
    And a treasure is hidden in Treasure room

    When Bilbo plays a turn
    Then Bilbo is now in the Intermediate room
    When Bilbo plays a turn
    Then Bilbo is now in the Treasure room
    When Bilbo plays a turn
    Then Bilbo is still in the Treasure room
      # Fighting and beating the BlackKnight

    When Bilbo plays a turn
    Then Bilbo is still in the Treasure room
    And Bilbo has found the treasure

    # Because treasure is found
    Then the game is over
    And Bilbo is still alive