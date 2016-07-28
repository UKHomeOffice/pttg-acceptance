package steps

import cucumber.api.DataTable
import cucumber.api.java.Before
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import net.thucydides.core.annotations.Managed
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

import static java.util.concurrent.TimeUnit.SECONDS
import static steps.Utils.verifyHealthChecks

/**
 * @Author Home Office Digital
 */
class FinancialStatusSteps {

    def fsUiRoot = "http://localhost:8001"

    def delay = 500

    @Managed
    WebDriver driver

    Utils utils

    @Before
    def setUp() {

        utils = new Utils(driver)

        utils.pageLocations = [
            'studentType'       : '#/financial-status-student-type',
            'doctorateQuery'    : '#/financial-status-query-doctorate',
            'non-doctorateQuery': '#/financial-status-query-non-doctorate',
            'accountNotFound'   : '#/financial-status-no-record'
        ]

        utils.pageUrls = [
            'studentType'       : fsUiRoot,
            'doctorateQuery'    : fsUiRoot + '#/financial-status-query-doctorate',
            'non-doctorateQuery': fsUiRoot + '#/financial-status-query-non-doctorate'
        ]

    }


    def sortCodeParts = ["First", "Second", "Third"]
    def sortCodeDelimiter = "-"

    def dateParts = ["Day", "Month", "Year"]
    def dateDelimiter = "/"

    def inLondonRadio = new Utils.RadioButtonConfig()
        .withOption('yes', 'inLondon-1')
        .withOption('no', 'inLondon-2')

    def studentTypeRadio = new Utils.RadioButtonConfig()
        .withOption('non-doctorate', 'studentType-1')
        .withOption('doctorate', 'studentType-2')

    def studentType


    def submitEntries(Map<String, String> entries) {
        entries.each { k, v ->
            String key = Utils.toCamelCase(k)

            if (key.endsWith("Date")) {
                utils.fillOrClearBySplitting(key, v, dateParts, dateDelimiter)

            } else if (key == "sortCode") {
                utils.fillOrClearBySplitting(key, v, sortCodeParts, sortCodeDelimiter)

            } else {
                def element = driver.findElement(By.id(key))

                if (key == "inLondon") {
                    utils.clickRadioButton(inLondonRadio, v)

                } else if (key == "studentType") {
                    utils.clickRadioButton(studentTypeRadio, v)
                } else {
                    Utils.sendKeys(element, v)
                }
            }
        }

        sleep(delay)
        driver.findElement(By.className("button")).click()
    }

    def chooseAndSubmitStudentType(String type) {
        selectStudentType(type)
        submitStudentTypeChoice()
    }

    def selectStudentType(String type) {
        utils.clickRadioButton(studentTypeRadio, type)
    }

    def submitStudentTypeChoice() {
        utils.clickButton()
    }

    @Given("^(?:caseworker|user) is using the financial status service ui\$")
    def user_is_using_the_financial_status_service_ui() throws Throwable {
        driver.get(fsUiRoot)
        utils.assertCurrentPage('studentType')
    }

    @Given("^the (.*) student type is chosen\$")
    def the_student_type_is_chosen(String type) {
        studentType = type
        chooseAndSubmitStudentType(type)
    }

    @When("^the student type choice is submitted\$")
    def the_student_type_choice_is_submitted() {
        submitStudentTypeChoice()
    }

    @When("^the financial status check is performed\$")
    def the_financial_status_check_is_performed() throws Throwable {

        Map<String, String> validDefaultEntries = [
            'End date'                       : '30/05/2016',
            'In London'                      : 'Yes',
            'Course start date'              : '30/05/2016',
            'Course end date'                : '30/06/2016',
            'Accommodation fees already paid': '0',
            'Number of dependants'           : '1',
            'Sort code'                      : '11-11-11',
            'Account number'                 : '11111111',
        ]

        if (studentType.equalsIgnoreCase('non-doctorate')) {
            validDefaultEntries['Total tuition fees'] = '1';
            validDefaultEntries['Tuition fees already paid'] = '0';
        }

        submitEntries(validDefaultEntries)
    }

    @When("^the financial status check is performed with\$")
    def the_financial_status_check_is_performed_with(DataTable arg1) throws Throwable {
        Map<String, String> entries = arg1.asMap(String.class, String.class)
        submitEntries(entries)
    }


    @Then("^the health check response status should be (\\d+)\$")
    def the_health_check_response_status_should_be(int expected) {
        sleep(800) // Seems to need a delay to let wiremock catch up
        assert Utils.responseStatusFor(fsUiRoot + "health") == expected
    }
}