@API
Feature: Add And Delete candidate Thru APIs


  Scenario: Add candidate thru API
    Given Login with username "Admin" and password "admin123"
    When Add new candidate using "recruitment/candidates"
    Then Assert on status code and response body

  Scenario: Remove candidate thru API
    Given Login with username "Admin" and password "admin123"
    When Delete candidate using "recruitment/candidates"
    Then Assert candidate is Deleted