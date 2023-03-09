Feature: Testing different request on the student application


  Scenario: Check if the student application can be accessed by users
    When User sends a GET request to list endpoint
    Then User must get back a valid status code 200

  Scenario Outline: Create a new student & verify if the student is added
    When I create a new student by providing the information firstName "<firstName>" lastName "<lastName>" email "<email>" programme "<programme>" courses "<courses>"
    Then I verify that the student with "<email>" is created
    Examples:
      | firstName | lastName | email         | programme        | courses |
      | sunny     | Dev      | Dev@gmail.com | Software testing | Diploma |
      | Rose      | Das      | Das@gmail.com | Computer science | Master  |

    Scenario: check if the student info has been updated and verify
      When new student updated with "<firstname>"

     Scenario: Check if the student id is deleted by id and verify
       When I have deleted student id
       Then I verify that student id is deleted












