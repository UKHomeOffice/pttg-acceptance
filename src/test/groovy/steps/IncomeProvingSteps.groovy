package steps

import cucumber.api.DataTable
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import net.thucydides.core.annotations.Managed
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

import java.time.Duration
import java.time.Instant

/**
 * @Author Home Office Digital
 */
class IncomeProvingSteps {

    //def fsUiRoot = "http://localhost:8000"
    //def genericUiRoot = "https://pttg-ip-gt-ui-test.notprod.homeoffice.gov.uk"
    def genericUiRoot = "https://pttg-ip-gt-ui-dev.notprod.homeoffice.gov.uk"
    //def familyUiRoot = "https://pttg-ip-fm-ui-test.notprod.homeoffice.gov.uk"
    def familyUiRoot = "https://pttg-ip-fm-ui-dev.notprod.homeoffice.gov.uk"
    def delay = 500

Instant start;

  Duration gap = Duration.ofMinutes(20);

    @Managed
    WebDriver driver

    Utils utils

    def dateParts = ["Day", "Month", "Year"]
    def dateDelimiter = "/"

    @Before
    def setUp(){
        utils = new Utils(driver)
        //while(gap < Duration.ofMinutes(20)) {
          //  driver = true;
       // }
    }
@After
def tearDown(){
   // Instant later = start.plus(gap);

}
    def submitEntries(Map<String, String> entries) {
        entries.each { k, v ->
            String key = Utils.toCamelCase(k)

            if (key.endsWith("Date")) {
                utils.fillOrClearBySplitting(key, v, dateParts, dateDelimiter)

            } else {
                def element = driver.findElement(By.id(key))
                Utils.sendKeys(element, v)
            }
        }

        sleep(delay)
        driver.findElement(By.className("button")).click()
    }


    @Given("^caseworker is using the IPS Generic Tool\$")
    def user_is_using_the_IPS_Generic_Tool() throws Throwable {
        driver.get(genericUiRoot);
    }

    @Given("^caseworker is using the IPS Family Tool\$")
    def user_is_using_the_IPS_family_Tool() throws Throwable {
        driver.manage().deleteAllCookies()
        driver.get(familyUiRoot);
        //driver.get(fsUiRoot)
    }

    @When("^the (?:generic|family) income check is performed with\$")
    def generic_income_check_performed(DataTable arg1) {
        Map<String, String> entries = arg1.asMap(String.class, String.class)
        submitEntries(entries)
    }


    @Then("^the service displays the following income details\$")
    def service_displays_income_details(DataTable table){

        def rows = table.cells(0)

        rows.eachWithIndex { row, rowIndex ->

            row.eachWithIndex { expected, cellIndex ->

                def cellXpath = '//*[@id="results"]/tbody/tr[' + (rowIndex+2) + ']/td[' + (cellIndex+1) + ']'

                def actual = driver.findElement(By.xpath(cellXpath)).getText()

                assert actual == expected
            }
        }
    }
}
