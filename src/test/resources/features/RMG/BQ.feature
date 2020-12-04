Feature: Live Services: Big Query

  @RMG
  Scenario: Big Query
    Given the BQ query is "query1"
    When the project is "rmg-ppd-gbi-coredata"
    Then query result is non-null value




