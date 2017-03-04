Feature: Financial Status core use case scenarios

    Background:
        Given caseworker is using the financial status service ui
        Given the caseworker has logged into key cloak using the following
            | User name | pttg-test |
            | Password  | pttg-test |

    Scenario: Tier five main applicant, sufficient funds

        And the Tier5 menu option is selected
        And Main applicant type is selected
        And the caseworker selects the Yes, check Barclays radio button
        And consent is sought for the following:
            | Sort code      | 01-06-16   |
            | Account number | 00005000   |
            | DOB            | 27/07/1981 |
        When the financial status check is performed with
            | Application raised date | 30/06/2016 |
            | End Date                | 31/05/2016 |
            | Dependants              | 0          |

        Then the service displays the following result
            | Outcome                    | Passed                   |
            | Application raised date    | 30/06/2016               |
            | Total funds required       | £945.00                  |
            | Maintenance period checked | 03/03/2016 to 31/05/2016 |
            | Account holder name        | Ray Purchase             |
            | Sort code                  | 01-06-16                 |
            | Account number             | 00005000                 |
            | Dependants                 | 0                        |
            | DOB                        | 27/07/1981               |
#        And the your result section displays the following
#            | Account holder name | Ray Purchase |

    Scenario: Tier two main applicant, sufficient funds

        And the Tier2 menu option is selected
        And Main applicant type is selected
        And the caseworker selects the Yes, check Barclays radio button
        And consent is sought for the following:
            | Sort code      | 01-06-16   |
            | Account number | 00005000   |
            | DOB            | 27/07/1981 |
        When the financial status check is performed with
            | Application raised date | 30/06/2016 |
            | End Date                | 31/05/2016 |
            | Dependants              | 0          |

        Then the service displays the following result
            | Outcome                    | Passed                   |

            | Total funds required       | £945.00                  |
            | Maintenance period checked | 03/03/2016 to 31/05/2016 |
            | Account holder name        | Ray Purchase             |
            | Sort code                  | 01-06-16                 |
            | Account number             | 00005000                 |
            | Dependants                 | 0                        |
            | DOB                        | 27/07/1981               |

    Scenario: Non-Doctorate, in London, insufficient funds

        And the Tier4 menu option is selected
        And Main applicant type is selected
        And the general-student student type is chosen
        And the caseworker selects the Yes, check Barclays radio button
        And consent is sought for the following:
            | Sort code      | 01-06-16   |
            | Account number | 00005000   |
            | DOB            | 27/07/1981 |
        When the financial status check is performed with
            | Application raised date         | 30/06/2016 |
            | End Date                        | 31/05/2016 |
            | Dependants                      | 0          |
            | In London                       | Yes        |
            | Course start date               | 30/05/2016 |
            | Course end date                 | 30/11/2016 |
            | Total tuition fees              | 8500.00    |
            | Tuition fees already paid       | 0          |
            | Accommodation fees already paid | 0          |
            | Dependants                      | 0          |
            | Continuation Course             | No         |
            | Course type                     | Main       |
            | Course institution              | true       |

        Then the service displays the following result
            | Outcome                    | Not passed               |
            | Application raised date    | 30/06/2016               |
            | Total funds required       | £17,355.00               |
            | Lowest balance             | £5,000.00 on 04/05/2016  |
            | Estimated leave end date   | 30/01/2017               |
            | Condition code             | 2 - Applicant            |
            | Maintenance period checked | 04/05/2016 to 31/05/2016 |
            | Account holder name        | Ray Purchase             |
            | Sort code                  | 01-06-16                 |
            | Account number             | 00005000                 |
            | Course length              | 7                        |
            | Dependants                 | 0                        |
            | DOB                        | 27/07/1981               |


    Scenario: Non-Doctorate, in London, insufficient funds - Other Institution

        And the Tier4 menu option is selected
        And Main applicant type is selected
        And the general-student student type is chosen
        And the caseworker selects the Yes, check Barclays radio button
        And consent is sought for the following:
            | Sort code      | 01-06-16   |
            | Account number | 00005000   |
            | DOB            | 27/07/1981 |
        When the financial status check is performed with
            | Application raised date         | 30/06/2016 |
            | End Date                        | 31/05/2016 |
            | Dependants                      | 0          |
            | In London                       | Yes        |
            | Course start date               | 30/05/2016 |
            | Course end date                 | 30/11/2016 |
            | Total tuition fees              | 8500.00    |
            | Tuition fees already paid       | 0          |
            | Accommodation fees already paid | 0          |
            | Dependants                      | 0          |
            | Continuation Course             | No         |
            | Course type                     | Main       |
            | Course institution              | false      |

        Then the service displays the following result
            | Outcome                    | Not passed               |
            | Total funds required       | £17,355.00               |
            | Lowest balance             | £5,000.00 on 04/05/2016  |
            | Estimated leave end date   | 30/01/2017               |
            | Course length              | 7                        |
            | Condition code             | 3 - Applicant            |
            | Application raised date    | 30/06/2016               |
            | Maintenance period checked | 04/05/2016 to 31/05/2016 |
            | Account holder name        | Ray Purchase             |
            | Sort code                  | 01-06-16                 |
            | Account number             | 00005000                 |
            | Dependants                 | 0                        |
            | DOB                        | 27/07/1981               |


    Scenario: Non-Doctorate, in London, insufficient funds - Dependent

        And the Tier4 menu option is selected
        And Dependent applicant type is selected
        And the general-student student type is chosen
        And the caseworker selects the Yes, check Barclays radio button
        And consent is sought for the following:
            | Sort code      | 01-06-16   |
            | Account number | 00005000   |
            | DOB            | 27/07/1981 |
        When the financial status check is performed with
            | Application raised date | 30/06/2016 |
            | End Date                | 31/05/2016 |
            | In London               | Yes        |
            | Course start date       | 30/05/2016 |
            | Course end date         | 30/11/2016 |
            | Dependants              | 1          |
            | Continuation Course     | No         |
            | Course institution      | true       |
            | Course type             | Main       |

        Then the service displays the following result
            | Outcome                    | Not passed               |
            | Application raised date    | 30/06/2016               |
            | Total funds required       | £7,605.00                |
            | Lowest balance             | £5,000.00 on 04/05/2016  |
            | Maintenance period checked | 04/05/2016 to 31/05/2016 |
            | Account holder name        | Ray Purchase             |
            | Sort code                  | 01-06-16                 |
            | Account number             | 00005000                 |
            | Dependants                 | 1                        |
            | DOB                        | 27/07/1981               |

    Scenario: DES, in London, Sufficient funds

        And the Tier4 menu option is selected
        And Main applicant type is selected
        And the DES student type is chosen
        And the caseworker selects the Yes, check Barclays radio button
        And consent is sought for the following:
            | Sort code      | 01-06-16   |
            | Account number | 00005000   |
            | DOB            | 27/07/1981 |
        When the financial status check is performed with
            | Application raised date         | 30/06/2016 |
            | End Date                        | 31/05/2016 |
            | In London                       | Yes        |
            | Accommodation fees already paid | 0          |
            | Dependants                      | 0          |
        Then the service displays the following result
            | Outcome                    | Passed                   |
            | Application raised date    | 30/06/2016               |
            | Total funds required       | £2,530.00                |
            | Maintenance period checked | 04/05/2016 to 31/05/2016 |
            | Account holder name        | Ray Purchase             |
            | Condition code             | 4E - Applicant           |
            | Sort code                  | 01-06-16                 |
            | Account number             | 00005000                 |
            | Dependants                 | 0                        |
            | DOB                        | 27/07/1981               |

    Scenario: DES, in London, Sufficient funds - Dependent

        And the Tier4 menu option is selected
        And Dependent applicant type is selected
        And the DES student type is chosen
        And the caseworker selects the Yes, check Barclays radio button
        And consent is sought for the following:
            | Sort code      | 01-06-16   |
            | Account number | 00005000   |
            | DOB            | 27/07/1981 |
        When the financial status check is performed with
            | Application raised date | 30/06/2016 |
            | End Date                | 31/05/2016 |
            | In London               | Yes        |
            | Dependants              | 1          |
        Then the service displays the following result
            | Outcome                    | Passed                                   |
            | Application raised date    | 30/06/2016                               |
            | Total funds required       | £1,690.00                                |
            | Maintenance period checked | 04/05/2016 to 31/05/2016                 |
            | Condition code             | 4B - Adult dependant,1 - Child dependant |
            | Account holder name        | Ray Purchase                             |
            | Sort code                  | 01-06-16                                 |
            | Account number             | 00005000                                 |
            | Dependants                 | 1                                        |
            | DOB                        | 27/07/1981                               |

    Scenario: pgdd, in London, Sufficient funds

        And the Tier4 menu option is selected
        And Main applicant type is selected
        And the pgdd student type is chosen
        And the caseworker selects the Yes, check Barclays radio button
        And consent is sought for the following:
            | Sort code      | 01-06-16   |
            | Account number | 00005000   |
            | DOB            | 27/07/1981 |
        When the financial status check is performed with
            | Application raised date         | 30/06/2016 |
            | End Date                        | 31/05/2016 |
            | Dependants                      | 0          |
            | In London                       | Yes        |
            | Course start date               | 30/05/2016 |
            | Course end date                 | 30/11/2016 |
            | Accommodation fees already paid | 0          |
            | Dependants                      | 0          |
            | Continuation Course             | No         |
        Then the service displays the following result
            | Outcome                    | Passed                   |
            | Total funds required       | £2,530.00                |
            | Application raised date    | 30/06/2016               |
            | Maintenance period checked | 04/05/2016 to 31/05/2016 |
            | Estimated leave end date   | 30/12/2016               |
            | Account holder name        | Ray Purchase             |
            | Course length              | 7 (limited to 2)         |
            | Condition code             | 2 - Applicant            |
            | Sort code                  | 01-06-16                 |
            | Account number             | 00005000                 |
            | Dependants                 | 0                        |
            | DOB                        | 27/07/1981               |


    Scenario: Not enough records found

        And the Tier4 menu option is selected
        And Dependent applicant type is selected
        And the suso student type is chosen
        And the caseworker selects the Yes, check Barclays radio button
        And consent is sought for the following:
            | Sort code      | 11-11-11   |
            | Account number | 11111111   |
            | DOB            | 27/07/1981 |
        When the financial status check is performed with
            | End date                | 10/06/2016 |
            | Application raised date | 20/06/2016 |
            | In London               | No         |
            | Course start date       | 30/05/2016 |
            | Course end date         | 30/11/2016 |
            | Dependants              | 1          |
            | Continuation Course     | No         |
        Then the service displays the following page content
            | Outcome        | Not passed                                                          |
            | Outcome detail | The records for this account does not cover the whole 28 day period |
        And the service displays the following your search data
            | Sort Code      | 11-11-11   |
            | Account Number | 11111111   |
            | DOB            | 27/07/1981 |

    Scenario: Calculator

        And the Tier4 menu option is selected
        And Main applicant type is selected
        And the suso student type is chosen
        And the caseworker selects the No radio button
        When the financial status check is performed with
            | End date                        | 10/06/2016 |
            | Application raised date         | 20/06/2016 |
            | In London                       | No         |
            | Accommodation fees already paid | 1265.01    |
            | Course start date               | 30/05/2016 |
            | Course end date                 | 30/11/2016 |
            | Dependants                      | 0          |
            | Continuation Course             | No         |
        Then the service displays the following page content
            | Total funds required            | £765.00                          |
            | Maintenance period checked      | 14/05/2016 to 10/06/2016         |
            | Course length                   | 7 (limited to 2)                 |
            | Tier                            | Tier 4 (General)                 |
            | Applicant type                  | Student union sabbatical officer |
            | In London                       | No                               |
            | Course dates checked            | 30/05/2016 to 30/11/2016         |
            | Accommodation fees already paid | £1,265.01 (limited to £1,265.00) |
            | Dependants                      | 0                                |
            | Application raised date         | 20/06/2016                       |
            | Continuation Course             | No                               |


    Scenario: Tier five dependent, sufficient funds

        And the Tier5 menu option is selected
        And Dependent applicant type is selected
        And the caseworker selects the Yes, check Barclays radio button
        And consent is sought for the following:
            | Sort code      | 01-06-16   |
            | Account number | 00005000   |
            | DOB            | 27/07/1981 |
        When the financial status check is performed with
            | Application raised date | 30/06/2016 |
            | End Date                | 31/05/2016 |
            | Dependants              | 1          |

        Then the service displays the following result
            | Outcome                    | Passed                   |
            | Application raised date    | 30/06/2016               |
            | Total funds required       | £630.00                  |
            | Maintenance period checked | 03/03/2016 to 31/05/2016 |
            | Account holder name        | Ray Purchase             |
            | Sort code                  | 01-06-16                 |
            | Account number             | 00005000                 |
            | Dependants                 | 1                        |
            | DOB                        | 27/07/1981               |


    Scenario: Tier two dependent, sufficient funds

        And the Tier2 menu option is selected
        And Dependent applicant type is selected
        And the caseworker selects the Yes, check Barclays radio button
        And consent is sought for the following:
            | Sort code      | 01-06-16   |
            | Account number | 00005000   |
            | DOB            | 27/07/1981 |
        When the financial status check is performed with
            | Application raised date | 30/06/2016 |
            | End Date                | 31/05/2016 |
            | Dependants              | 1          |

        Then the service displays the following result
            | Outcome                    | Passed                   |
            | Application raised date    | 30/06/2016               |
            | Total funds required       | £630.00                  |
            | Maintenance period checked | 03/03/2016 to 31/05/2016 |
            | Account holder name        | Ray Purchase             |
            | Sort code                  | 01-06-16                 |
            | Account number             | 00005000                 |
            | Dependants                 | 1                        |
            | DOB                        | 27/07/1981               |





