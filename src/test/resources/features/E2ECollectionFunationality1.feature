Feature: End-2-End Test Case Functionality 1

  @e2e1-1 @somemore
    @someextra
  Scenario: [E2E1-0] Some description 0
    Given source: mysql and inputKey: "select * from users where name='Anshul'"
    Given T0 call CheckJourney @id:2 @rtvm0 @jira0
    Given T1 call CheckJourney @id:4 @rtvm1 @jira1
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given T+2 call DoNothing @xyz @rtvm2 @jira2


#  Scenario: [E2E1-1] Some description 1
#    Given source: mysql and inputKey: "select * from users where name='Anshul'"
#    Given call CheckJourney.feature @id:2
#    Given call CheckJourney.feature @id:4
#    Given call DoNothing.feature @id:6


  Scenario: [E2E1-2] Some description 2
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given T0 call DoNothing.feature @id:6 @TC:rtvm0 @jira0
    Given some random step in between
    Given some random step 2 in between
    Given some random step 3 in between
    Given T1 call CheckJourney @id:4 @rtvm1
    Given T3 call DoNothing.feature @ bid:7 @rtvm3 @jira3
    Given T2 call CheckJourney @id:4 @rtvm2 @jira2

  Scenario: [E2E1-3] Some description 3
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given T0 call DoNothing.feature @id:6 @rtvm @jira
    Given some randon step 3 in between
      | head1 | head2 | head3 |
      | v1    | v2    | v3    |
      | v1    | v2    | v3    |
      | v1    | v2    | v3    |
    Given T-1 call DoNothing.feature @id:8 @rtvm @jira
    Given T+2 call DoNothing.feature @id:7 @rtvm @jira
#
#  Scenario: [] Some description 4
#    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
#    Given call DoNothing.feature @id:6
#
  Scenario: Some description 4
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given T+2 call DoNothing.feature @id:7 @rtvm @jira

  Scenario: [E2E1- 3] Some description 4
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given T+2 call DoNothing.feature @id:7 @rtvm @jira

  Scenario: [E2E1--3] Some description 6
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given T+2 call DoNothing.feature @id:7 @rtvm @jira

  Scenario: [-E2E1-3] Some description 7
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given T+2 call DoNothing.feature @id:7 @rtvm @jira

  Scenario: [E2E1-3-] Some description 8
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given T+2 call DoNothing.feature @id:7 @rtvm @jira

  Scenario: [E2E1@-Z] Some description 9
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given T+2 call DoNothing.feature @id:7 @rtvm @jira

  Scenario: [E2E1%-Z] Some description 9
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given T+2 call DoNothing.feature @id:7 @rtvm @jira

  Scenario: [ID:E2E11] Some description 11
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given T+2 call DoNothingInvalid.feature @id:7 @rtvm @jira

  Scenario: [ID:E2E1] Some description 11
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
    Given T+2 call DoNothing.feature @id:7 @rtvm @jira
