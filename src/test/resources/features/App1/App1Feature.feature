Feature: Delegate Journey Planner
  As an interested conference delegate
  I want to view journey details to the venue
  So that I can find the best way of getting to the conference

  @journeyplannerx @id:app1-1
  Scenario: Check Journey Manchester by car
    Given call data1
    Given Call data2
    Given the application "HealthConference"
    When a conference delegate checks their travel details from "Manchester, UK" by "car"
    Then the correct journey information will be calculated as "352 km" and "4 hours 0 mins"

  @journeyplanner @id:app1-2 @XRAY:IREQ-123  @20398heo
  Scenario: Check Journey Bath by car
    Given the application "HealthConference"
    When a conference delegate checks their travel details from "Bath, UK" by "car"
    Then the correct journey information will be calculated as "255 km" and "2 hours 55 mins"

  @journeyplanner @id:app1-3
  Scenario Outline: Check Journey <location> by <travel type>
    Given the application "HealthConference"
    When a conference delegate checks their travel details from "<location>" by "<travel type>"
    Then the correct journey information will be calculated as "<distance>" and "<duration>"
    Examples:
      | location       | travel type | distance | duration        |
      | NE1            | car         | 456 km   | 4 hours 53 mins |
      | YO1 7HH        | car         | 341 km   | 3 hours 47 mins |
      | Romford, Essex | car         | 22.6 km  | 31 mins         |
      | Islington      | car         | 11.2 km  | 28 mins         |

  @journeyplanner @id:app1-4
  Scenario: Check Various Journeys
    Given the application "HealthConference"
    When a trainee checks their travel details
    Then the journey information will be calculated correctly
      | location       | travel type | distance | duration        |
      | Manchester, UK | car         | 352 km   | 4 hours 0 mins  |
      | Bath, UK       | car         | 255 km   | 2 hours 55 mins |
      | NE1            | car         | 456 km   | 4 hours 53 mins |
      | YO1 7HH        | car         | 341 km   | 3 hours 47 mins |
      | Romford, Essex | car         | 22.6 km  | 31 mins         |
      | Islington      | car         | 11.2 km  | 28 mins         |
