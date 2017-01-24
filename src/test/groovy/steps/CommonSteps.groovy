package steps

import cucumber.api.DataTable
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import net.thucydides.core.annotations.Managed
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

import static java.util.concurrent.TimeUnit.SECONDS

/**
 * @Author Home Office Digital
 */
class CommonSteps {

    def defaultTimeout = 2000

    @Managed
    WebDriver driver

    Utils utils

    @Before
    def setUp() {
        utils = new Utils(driver)
    }

    @After
    def tearDown(){
        driver.manage().deleteAllCookies()
    }
    def static toCamelCase(String s) {
        if (s.isEmpty()) return ""
        def words = s.tokenize(" ")*.toLowerCase()*.capitalize().join("")
        words[0].toLowerCase() + words.substring(1)
    }

    @Then("^the service displays the following result\$")
    def the_service_displays_the_following_result(DataTable expectedResult) throws Throwable {
        Map<String,String> entries = expectedResult.asMap(String.class,String.class)

        Thread.sleep(1500)
        assert driver.getCurrentUrl().contains("result")
        utils.assertTextFieldEqualityForTable(expectedResult)
       // assert driver.getCurrentUrl().contains("result")


        ArrayList<String> scenarioTable = new ArrayList<>()
        String[] resultTable = entries.keySet()

        for(String s:resultTable){
            scenarioTable.add(entries.get(s))
        }

        for (int j = 0; j < resultTable.size(); j++) {
            assert scenarioTable.contains(driver.findElement(By.id(toCamelCase(resultTable[j]))).getText())
        }


        int numRows = driver.findElements(By.xpath('//*[@id="resultsTable"]/tbody/tr')).size()
        int yourSearchRows = driver.findElements(By.xpath('//*[@id="yourSearchTable"]/tbody/tr')).size()

        for(int i=1; i <= numRows; i++) {
            if (driver.findElement(By.id("resultTimestamp")).getText() != driver.findElement(By.xpath('//*[@id="resultsTable"]/tbody/tr[' + i + ']/td')).getText()) {
                if (driver.findElement(By.xpath('//*[@id="resultsTable"]/tbody/tr[' + i + ']/td')).getText() == null) {
                    break;
                }
                assert scenarioTable.contains(driver.findElement(By.xpath('//*[@id="resultsTable"]/tbody/tr[' + i + ']/td')).getText())
            }
        }

        for(int j=1; j <= yourSearchRows; j++){
            if (driver.findElement(By.xpath('//*[@id="yourSearchTable"]/tbody/tr[' + j + ']/td')).getText() == null) {
                break;
            }
            assert scenarioTable.contains(driver.findElement(By.xpath('//*[@id="yourSearchTable"]/tbody/tr[' + j + ']/td')).getText())
        }

    }

    @Then("^the service displays the following your search data\$")
    def the_service_displays_the_following_your_search_date(DataTable expectedResult) throws Throwable {
        utils.assertTextFieldEqualityForTable(expectedResult)
    }

    @When("^the caseworker views the (.*) page\$")
    def the_caseworker_views_the_query_page(String pageName) throws Throwable {
        utils.goToPage(pageName)
    }

    @When("^after at least (\\d+) seconds\$")
    def static after_at_least_x_seconds(int seconds) {
        sleep(seconds * 1000);
    }

    @Then("^the service displays the following message\$")
    def the_service_displays_the_following_message(DataTable arg1) throws Throwable {
        Map<String, String> entries = arg1.asMap(String.class, String.class)
        utils.assertTextFieldEqualityForMap(entries)
    }

    @Then("^the service displays the (.*) page\$")
    def the_service_displays_the_named_page(String pageName) throws Throwable {
        utils.assertCurrentPage(Utils.toCamelCase(pageName))
    }

    @Then("^the service displays the (.*) page sub heading\$")
    def the_service_displays_the_page_sub_heading(String pageSubHeading) throws Throwable {
        utils.assertTextFieldEqualityForMap(['page sub heading': pageSubHeading])
    }

    @Then("^the service displays the following page content\$")
    def the_service_displays_the_following_page_content(DataTable expectedResult) throws Throwable {
        utils.assertTextFieldEqualityForTable(expectedResult)
    }

    @Then("^the service displays the following page content within (\\d+) seconds\$")
    def the_service_displays_the_following_page_content_within_seconds(long timeout, DataTable expectedResult) throws Throwable {
        driver.manage().timeouts().implicitlyWait(timeout, SECONDS)
        utils.assertTextFieldEqualityForTable(expectedResult)
        driver.manage().timeouts().implicitlyWait(defaultTimeout, SECONDS)
    }

    @Then("^the service displays the following (.*) headers in order\$")
    def the_service_displays_the_following_your_search_headers_in_order(String tableName, DataTable expectedResult) throws Throwable {
        def tableId = Utils.toCamelCase(tableName) + "Table"
        utils.verifyTableRowHeadersInOrder(expectedResult, tableId)
    }

    @Then("^the error summary list contains the text\$")
    def the_error_summary_list_contains_the_text(DataTable expectedText) {
        utils.verifyErrorSummaryText(expectedText)
    }

}
