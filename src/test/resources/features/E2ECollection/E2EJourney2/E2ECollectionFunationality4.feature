Feature: End-2-End Test Case Functionality 1

  @e2e1-1 @somemore
    @someextra
  Scenario: [E2E3-1] Some description 1
    Given source: mysql and inputKey: "select * from users where name='Anshul'"
    Given call CheckJourney.feature @id:2
    Given call CheckJourney.feature @id:4
    Given call DoNothing.feature @id:6


  Scenario: [E2E3-2] Some description 2
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given call DoNothing.feature @id:6
    Given some randon step in between
    Given some randon step 2 in between
    Given call CheckJourney.feature @id:4
    Given call DoNothing.feature @id:7

  Scenario: [E2E3-3] Some description 3
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given call DoNothing.feature @id:6
    Given some randon step 3 in between
      | head1 | head2 | head3 |
      | v1    | v2    | v3    |
      | v1    | v2    | v3    |
      | v1    | v2    | v3    |
    Given call DoNothing.feature @id:8
    Given call DoNothing.feature @id:7