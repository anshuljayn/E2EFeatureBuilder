Feature: Live Services - Dataproc jobs
  @RMG
  Scenario: DataProc Jobs
    Given I want to validate dataproc jobs
    When the project is "rmg-ppd-gbi-coredata"
    Then following dataproc jobs status is "DONE" for today's date
      | run_dataproc_summary_job |
      | run_dataproc_core_job    |




