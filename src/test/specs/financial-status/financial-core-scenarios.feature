Feature: Financial Status core use case scenarios

    Background:
        Given caseworker is using the financial status service ui
        Given the caseworker has logged into key cloak using the following
            | User name | mitchell |
            | Password  | password |

    Scenario: Non-doctorate, in London, insufficient funds

        And the non-doctorate student type is chosen
        When the financial status check is performed with
            | End date                        | 30/05/2016 |
            | In London                       | Yes        |
            | Course start date               | 30/05/2016 |
            | Course end date                 | 30/11/2016 |
            | Total tuition fees              | 8500.00    |
            | Tuition fees already paid       | 0          |
            | Accommodation fees already paid | 0          |
            | Number of dependants            | 0          |
            | Sort code                       | 11-11-11   |
            | Account number                  | 77777777   |
            | dob                             | 01/01/1996 |
        Then the service displays the following result
            | Outcome                         | Not passed               |
            | Total funds required            | £17,355.00               |
            | Maintenance period checked      | 03/05/2016 to 30/05/2016 |
            | Course dates checked            | 30/05/2016 to 30/11/2016 |
            | Student type                    | Tier 4 (General) student |
            | In London                       | Yes                      |
            | Course length                   | 7 (limited to 9)         |
            | Total tuition fees              | £8,500.00                |
            | Tuition fees already paid       | £0.00                    |
            | Accommodation fees already paid | £0.00                    |
            | Account holder name             | Ray Purchase             |
            | Sort code                       | 11-11-11                 |
            | Account number                  | 77777777                 |
            | DOB                             | 01/01/1996               |

    Scenario: Non-doctorate, not in London, sufficient funds

        And the non-doctorate student type is chosen
        When the financial status check is performed with
            | End date                        | 30/05/2016 |
            | In London                       | No         |
            | Course start date               | 30/05/2016 |
            | Course end date                 | 28/02/2017 |
            | Total tuition fees              | 15500.00   |
            | Number of dependants            | 0          |
            | Tuition fees already paid       | 100        |
            | Accommodation fees already paid | 1200       |
            | Sort code                       | 44-44-44   |
            | Account number                  | 13131313   |
            | DOB                             | 01/01/1996 |
        Then the service displays the following result
            | Outcome                    | Passed                   |
            | Total funds required       | £23,335.00               |
            | Maintenance period checked | 03/05/2016 to 30/05/2016 |
            | Course dates checked       | 30/05/2016 to 28/02/2017 |
            | Sort code                  | 44-44-44                 |
            | Account number             | 13131313                 |
            | Account holder name        | Ray Purchase             |
            | DOB                        | 01/01/1996               |

    Scenario: Doctorate, in London, insufficient funds

        And the doctorate student type is chosen
        When the financial status check is performed with
            | End date                        | 30/05/2016 |
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

        And the doctorate student type is chosen
        When the financial status check is performed with
            | End date                        | 30/05/2016 |
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

        And the doctorate student type is chosen
        When the financial status check is performed with
            | End date                        | 10/06/2016 |
            | In London                       | No         |
            | Accommodation fees already paid | 0          |
            | Number of dependants            | 0          |
            | Sort code                       | 99-99-99   |
            | Account number                  | 99999999   |
            | DOB                             | 27/05/1986 |
        Then the service displays the following page content
            | Page dynamic heading | There is no record for the sort code and account number with Barclays                                                           |
            | Page Dynamic detail  | We couldn't perform the financial requirement check as no information exists for sort code 99-99-99 and account number 99999999 |
        And the service displays the following your search data
            | Sort Code      | 99-99-99   |
            | Account Number | 99999999   |
            | DOB            | 27/05/1986 |


    Scenario: Not enough records found

        And the doctorate student type is chosen
        #Given the account does not have sufficient records
        When the financial status check is performed with
            | End date                        | 10/06/2016 |
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




