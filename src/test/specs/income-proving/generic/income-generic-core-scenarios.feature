Feature: Income Proving - generic tool - core use case scenarios

    Background:
        Given caseworker is using the IPS Generic Tool

    Scenario: Robert obtains NINO income details to understand how much they have earned within 6 months (single job)
        When the generic income check is performed with
            | NINO      | QQ123456A  |
            | From Date | 01/01/2015 |
            | To Date   | 30/06/2015 |
        Then the service displays the following income details
            | 03/01/2015 | Flying Pizza Ltd | £1,666.11 |
            | 03/02/2015 | Flying Pizza Ltd | £1,666.11 |
            | 05/03/2015 | Flying Pizza Ltd | £1,666.11 |
            | 03/04/2015 | Flying Pizza Ltd | £1,666.11 |
            | 03/05/2015 | Flying Pizza Ltd | £1,666.11 |
            | 03/06/2015 | Flying Pizza Ltd | £1,666.11 |
            | Total:     |                  | £9,996.66 |
        And the service displays the following your search data
            | Your Search Individual Name           | Harry Callahan |
            | Your Search National Insurance Number | QQ123456A      |
            | Your Search From Date                 | 01/01/2015     |
            | Your Search To Date                   | 30/06/2015     |

    Scenario: Error summary details are shown when a validation error occurs
        When the generic income check is performed with
            | NINO      |  |
            | From Date |  |
            | To Date   |  |
        Then the service displays the following message
            | validation-error-summary-heading | There's some invalid information                  |
            | validation-error-summary-text    | Make sure that all the fields have been completed |
        And the error summary list contains the text
            | The National Insurance Number is invalid |
            | The from date is invalid                 |
            | The to date is invalid                   |

    Scenario: Caseworker enters a NINO where no records exist within the period stated
        Given Robert is using the IPS Generic Tool
        When the generic income check is performed with
            | NINO      | KR123456C  |
            | From Date | 01/04/2015 |
            | To Date   | 20/12/2015 |
        Then the service displays the following message
            | Page dynamic heading  | There is no record for KR123456C with HMRC                                                                                                 |
            | Page dynamic sub text | We couldn't perform the financial requirement check as no income information exists with HMRC for the National Insurance Number KR123456C. |
        And the service displays the following your search data
            | Your Search National Insurance Number | KR123456C  |
            | Your Search From Date                 | 01/04/2015 |
            | Your Search To Date                   | 20/12/2015 |
