Feature: End-2-End Test Case Functionality 2

  Scenario: [E2E2-1] Some description 21
    Given source: mysql and inputKey: "select * from users where name='Anshul'"
    Given call CheckJourney.feature @id:1
    Given call CheckJourney.feature @id:3
    Given call DoNothing.feature @id:8


  Scenario: [E2E2-2] Some description 22
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given call CheckJourney.feature @id:1
    Given call DoNothing.feature @id:6
    Given call CheckJourney.feature @id:4
    Given call DoNothing.feature @id:
    Given call RMG\DP.feature @RMG

  Scenario: [E2E2-3] Some description 22
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given call DoNothing.feature @id:6
    Given call DoNothing.feature @id:
    Given call RMG\DP.feature @RMG

  Scenario: Some description 24
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given call DoNothing.feature @id:6
    Given call DoNothing.feature @id:
    Given call RMG\DP.feature @RMG
