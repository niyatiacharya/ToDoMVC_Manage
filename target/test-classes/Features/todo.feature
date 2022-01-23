#Author: Niyati Acharya
Feature: Manage Todo List

  Background: 
    Given the todoMVC site is open in the browser

  Scenario: Adding a task to an empty list
    Given a list with 0 tasks
    When a task is added
    Then 1 task is visible in the list
    And the number of items counter is set to 1
    And the filters are displayed

  Scenario: Adding a task to a non-empty list
    Given a list with 1 tasks
    When a task is added
    Then 2 task is visible in the list
    And the number of items counter is set to 2

  Scenario: Editing a task
    Given a list with 1 tasks
    When the task is edited
    Then the edited task is visible in the list
    And the number of items counter is set to 1

  Scenario: Completing a task
    Given a list with 2 tasks
    When 1 task is marked as completed
    Then the task is checked off from the list
    And the number of items counter is set to 1
    And the option to clear completed tasks is available

  Scenario Outline: Filter tasks
    Given a list with 2 tasks
    And 1 task is marked as completed
    When <filterType> filter is selected
    Then <filterType> tasks are displayed
    And the number of items counter is set to 1

    Examples: 
      | filterType  |
      | "Active"    |
      | "Completed" |
      | "All"       |

  Scenario: Clear Completed tasks
    Given a list with 2 tasks
    And 1 task is marked as completed
    When completed tasks are cleared
    Then the completed tasks are not displayed
    But the active tasks are displayed
    And the number of items counter is set to 1

  Scenario Outline: Delete task
    Given a list with 2 tasks
    And 1 task is marked as completed
    When <task> task is deleted
    Then <task> tasks is not displayed
    And the number of items counter is set to <count>

    Examples: 
      | task        | count |
      | "Completed" |     1 |
      | "Active"    |     0 |

  Scenario: Mark all tasks as completed
    Given a list with 2 tasks
    When All the tasks are marked completed
    Then the tasks are checked off from the list
    And the number of items counter is set to 0
