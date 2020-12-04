
Feature: Live Services: DataFlow Jobs

  @RMG
  Scenario: DataFlow Jobs
    Given I want to validate dataflow jobs
    When the project is "rmg-ppd-gbi-coredata"
    Then following dataflow jobs status is "JOB_STATE_DONE" for today's date
      | s_srv_req_xm |
      | atrributes-  |




