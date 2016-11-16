Feature: Income Proving - family tool - core use case scenarios

    Background:
        Given caseworker is using the IPS Family Tool
        Given the caseworker has logged into key cloak using the following
            | User name | pttg-test |
            | Password  | pttg-test |

    Scenario: Does not meet Cat A Financial Requirement (earned < the Cat A threshold)
    Pay date 15th of the month
    Before day of Application Raised Date
    She has 4 Canadian dependants
    She earns £2250.00 Monthly Gross Income EVERY of the 6 months
        When the family income check is performed with
            | NINO                    | SP123456B  |
            | Application Raised Date | 03/02/2015 |
            | Dependants              | 4          |
        Then the service displays the following result
            | Page dynamic heading                  | Shelly Patel doesn't meet the Category A requirement |
            | Page dynamic detail                   | they haven't met the required monthly amount.        |
            | Your Search Individual Name           | Shelly Patel                                         |
            | Your Search Dependants                | 4                                                    |
            | Your Search National Insurance Number | SP123456B                                            |
            | Your Search Application Raised Date   | 03/02/2015                                           |

    Scenario: Caseworker enters a NINO where no records exist within the period stated
        When the family income check is performed with
            | NINO                    | RK123456C  |
            | Application Raised Date | 03/07/2015 |
        Then the service displays the following result
            | Page dynamic heading                  | There is no record for RK123456C with HMRC                                                                                                 |
            | Page dynamic detail                 | We couldn't perform the financial requirement check as no income information exists with HMRC for the National Insurance Number RK123456C. |
            | Your Search National Insurance Number | RK123456C                                                                                                                                  |
            | Your Search Application Raised Date   | 03/07/2015                                                                                                                                 |

    Scenario: Caseworker does NOT enter a National Insurance Number
        When the family income check is performed with
            | NINO                    |            |
            | Application Raised Date | 01/01/2015 |
        Then the service displays the following result
            | nino-error | Enter a valid National Insurance Number |

    Scenario: Check for important text on the page
        When the family income check is performed with
            | NINO                    | JL123456A  |
            | Application Raised Date | 15/01/2015 |
        Then the service displays the following result
            | Page static detail          | They don't meet the financial requirement because:                                             |
            | What to do next heading     | What to do next                                                                                |
            | What to do next sub heading | You should consider if the applicant meets the financial requirement under any other category. |

    Scenario: Page checks for Category A financial text write up
        When the family income check is performed with
            | NINO                    | TL123456A  |
            | Application Raised Date | 23/01/2015 |
        Then the service displays the following result
            | Page static heading     | Financial requirement check                                   |
            | Page static sub heading | Does the applicant meet the Category A financial requirement? |

    Scenario: Page checks for appendix link
        When the family income check is performed with
            | NINO                    | TL123456A  |
            | Application Raised Date | 23/01/2015 |
        Then the service displays the following result
            | Page appendix title | Where can I find the appendix?                         |
            | Chapter 8 link      | Chapter 8 of the immigration directorate instructions. |
            | FM 1_7 link         | Appendix FM 1.7                                        |

    Scenario: Input Page checks for Category A financial text write up
        Then the service displays the following page content
            | Page sub title | Individual's details                                                                            |
            | Page sub text  | You can check an individual meets the Category A requirement using a National Insurance Number. |
