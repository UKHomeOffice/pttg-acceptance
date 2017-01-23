Feature: Financial Status core use case scenarios

    Background:
        Given caseworker is using the financial status service calculator
        Given the caseworker has logged into key cloak using the following
            | User name | pttg-test |
            | Password  | pttg-test |


    Scenario: Caseworker is using the calc ui.

        And the general-student student type is chosen
        When the financial status check is performed with
            | Application raised date         | 02/05/2016 |
            | End Date                        | 01/05/2016 |
            | Dependants                      | 0          |
            | In London                       | No         |
            | Course Start Date               | 30/05/2016 |
            | Course End Date                 | 29/07/2016 |
            | Total tuition fees              | 3000       |
            | Tuition fees already paid       | 2000       |
            | Accommodation fees already paid | 100        |
            | Continuation course             | No         |
            | Course type                     | Main       |
        Then the service displays the following result
                   # | Application raised date         | 02/05/2016 |
                   # | End Date                        | 01/05/2016 |
            | Dependants                      | 0          |
            | In London                       | No         |
            | Course Start Date               | 30/05/2016 |
            | Course End Date                 | 29/07/2016 |
            | Total tuition fees              | 3000       |
            | Tuition fees already paid       | 2000       |
            | Accommodation fees already paid | 100        |
