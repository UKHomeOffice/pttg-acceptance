Feature: Financial Status core use case scenarios

    Background:
        Given caseworker is using the financial status service ui
        Given the caseworker has logged into key cloak using the following
            | User name | pttg-test |
            | Password  | pttg-test |

    Scenario: Non-doctorate, in London, insufficient funds

        And the general-student student type is chosen
        When the financial status check is performed with
            | Application raised date         | 30/06/2016 |
            | End Date                        | 31/05/2016 |
            | In London                       | Yes        |
            | Course start date               | 30/05/2016 |
            | Course end date                 | 30/11/2016 |
            | Total tuition fees              | 8500.00    |
            | Tuition fees already paid       | 0          |
            | Accommodation fees already paid | 0          |
            | Dependants                      | 0          |
            | Sort code                       | 11-11-11   |
            | Account number                  | 11111111   |
            | Dob                             | 27/07/1981 |
            | Continuation Course             | No         |
            | Course type                     | Main       |
        Then the service displays the following result
            | Outcome                         | Not passed               |
            | Application raised date         | 30/06/2016               |
            | Accommodation fees already paid | £0.00                    |
            | Applicant type                  | Tier 4 (General) student |
            | Total funds required            | £17,355.00               |
            | Maintenance period checked      | 04/05/2016 to 31/05/2016 |
            | Course dates checked            | 30/05/2016 to 30/11/2016 |
            | Course type                     | Main                     |
            | In London                       | Yes                      |
            | Course length                   | 7                        |
            | Total tuition fees              | £8,500.00                |
            | Tuition fees already paid       | £0.00                    |
            | Accommodation fees already paid | £0.00                    |
            | Account holder name             | Ray Purchase             |
            | Sort code                       | 11-11-11                 |
            | Account number                  | 11111111                 |
            | Continuation Course             | No                       |
            | Estimated Leave End Date        | 30/01/2017               |
            | Dependants                      | 0                        |
            | DOB                             | 27/07/1981               |

    Scenario: Non-doctorate, not in London, sufficient funds

        And the general-student student type is chosen
        When the financial status check is performed with
            | Application raised date         | 20/06/2016 |
            | End Date                        | 30/05/2016 |
            | In London                       | Yes        |
            | Course start date               | 08/06/2016 |
            | Course end date                 | 20/08/2016 |
            | Total tuition fees              | 9755.50    |
            | Tuition fees already paid       | 3500       |
            | Accommodation fees already paid | 250.50     |
            | Dependants                      | 1          |
            | Sort code                       | 11-11-11   |
            | Account number                  | 77777777   |
            | Dob                             | 06/04/1989 |
            | Continuation Course             | Yes        |
            | Original Course Start Date      | 30/10/2015 |
            | Course type                     | Main       |
        Then the service displays the following result
            | Outcome                         | Passed                   |
            | Total funds required            | £14,025.00               |
            | Maintenance period checked      | 03/05/2016 to 30/05/2016 |
            | Course dates checked            | 08/06/2016 to 20/08/2016 |
            | Sort code                       | 11-11-11                 |
            | Account number                  | 77777777                 |
            | Course type                     | Main                     |
            | In London                       | Yes                      |
            | Continuation Course             | Yes                      |
            | Total tuition fees              | £9,755.50                |
            | Tuition fees already paid       | £3,500.00                |
            | Original course start date      | 30/10/2015               |
            | Account holder name             | Ray Purchase             |
            | DOB                             | 06/04/1989               |
            | Course length                   | 3                        |
            | Continuation Course             | Yes                      |
            | Estimated Leave End Date        | 20/10/2016               |
            | Entire course length            | 10                       |
            | Application raised date         | 20/06/2016               |
            | Applicant type                  | Tier 4 (General) student |
            | Accommodation fees already paid | £250.50                  |
            | Dependants                      | 1                        |


    Scenario: Doctorate, in London, insufficient funds

        And the DES student type is chosen
        When the financial status check is performed with
            | End date                        | 30/05/2016 |
            | Application raised date         | 20/06/2016 |
            | In London                       | Yes        |
            | Accommodation fees already paid | 0          |
            | Number of dependants            | 0          |
            | Sort code                       | 11-11-12   |
            | Account number                  | 11111112   |
            | DOB                             | 01/01/1996 |
        Then the service displays the following result
            | Outcome                         | Not passed                                            |
            | Total funds required            | £2,530.00                                             |
            | Maintenance period checked      | 03/05/2016 to 30/05/2016                              |
            | Student type                    | Tier 4 (General) student (doctorate extension scheme) |
            | In London                       | Yes                                                   |
            | Accommodation fees already paid | £0.00                                                 |
            | Account holder name             | Ray Purchase                                          |
            | Sort code                       | 11-11-12                                              |
            | Account number                  | 11111112                                              |
            | DOB                             | 01/01/1996                                            |

    Scenario: Doctorate, in London, sufficient funds
    balance in her account is >= than the Total funds required - at £2279.50)
    She has >= than the threshold for the previous 28 days

        And the DES student type is chosen
        When the financial status check is performed with
            | End date                        | 30/05/2016 |
            | Application raised date         | 20/06/2016 |
            | In London                       | Yes        |
            | Number of dependants            | 0          |
            | Accommodation fees already paid | 250.50     |
            | Sort code                       | 22-22-23   |
            | Account number                  | 88888889   |
            | DOB                             | 01/01/1996 |
        Then the service displays the following result
            | Outcome                    | Passed                                                |
            | Total funds required       | £2,279.50                                             |
            | Maintenance period checked | 03/05/2016 to 30/05/2016                              |
            | Student type               | Tier 4 (General) student (doctorate extension scheme) |
            | Sort code                  | 22-22-23                                              |
            | Account number             | 88888889                                              |
            | Account holder name        | Ray Purchase                                          |
            | DOB                        | 01/01/1996                                            |


    Scenario: No records exist within the period stated

        And the DES student type is chosen
        When the financial status check is performed with
            | End date                        | 10/06/2016 |
            | Application raised date         | 20/06/2016 |
            | In London                       | No         |
            | Accommodation fees already paid | 0          |
            | Number of dependants            | 0          |
            | Sort code                       | 99-99-99   |
            | Account number                  | 99999999   |
            | DOB                             | 27/05/1986 |
        Then the service displays the following page content
            | Page dynamic heading | Invalid or inaccessible account                                                  |
            | Page Dynamic detail  | One or more of the following conditions prevented us from accessing the account: |
        And the service displays the following your search data
            | Sort Code      | 99-99-99   |
            | Account Number | 99999999   |
            | DOB            | 27/05/1986 |


    Scenario: Not enough records found

        And the DES student type is chosen
        #Given the account does not have sufficient records
        When the financial status check is performed with
            | End date                        | 10/06/2016 |
            | Application raised date         | 20/06/2016 |
            | In London                       | No         |
            | Accommodation fees already paid | 0          |
            | Number of dependants            | 0          |
            | Sort code                       | 11-11-11   |
            | Account number                  | 11111111   |
            | DOB                             | 27/05/1986 |
        Then the service displays the following page content
            | Page dynamic heading | Not passed                                                          |
            | Page Dynamic detail  | The records for this account does not cover the whole 28 day period |
        And the service displays the following your search data
            | Sort Code      | 11-11-11   |
            | Account Number | 11111111   |
            | DOB            | 27/05/1986 |







