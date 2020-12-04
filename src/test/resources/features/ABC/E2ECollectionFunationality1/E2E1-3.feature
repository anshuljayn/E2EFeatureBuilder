# auto generated on Tue Oct 27 10:31:33 GMT 2020 by anshul.j.jain

@E2E1-3
Feature: [E2E1-3] Some description 3


  @T0 @TC:rtvm @jira
  @id:6 @TC:rtvm1
  Scenario: a failing scenario
#pre-requisite
    Given source: json and inputKey: "temp/inputJsonFile.JsonObjectKey"
#test-steps
    Given do nothing
    And a failing step


  @T-1 @rtvm @jira
    @id:8 @qowydoqwu @qwiugqwd @somemoretag
  Scenario Outline: some outline
#pre-requisite
    Given some randon step 3 in between
      | head1 | head2 | head3 |
      | v1    | v2    | v3    |
      | v1    | v2    | v3    |
      | v1    | v2    | v3    |
#test-steps
    Given call DoNothing.feature @id:6 "val"
    Examples:
      | TC | val |
      | 1  | A   |
      | 2  | B   |

    @example
    Examples:
      | TC | val |
      | 1  | X   |
      | 2  | Y   |


  @T+2 @rtvm @jira
    @outline @id:7
  Scenario Outline: outline scenario
    Given do nothing
    And a failing step
    Examples:
      | A |
      | 1 |
      | 2 |





