package steps

import com.jayway.restassured.response.Response
import cucumber.api.DataTable
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import static com.jayway.restassured.RestAssured.given
/**
 * @Author Home Office Digital
 */
class Utils {

    WebDriver driver

    def pageLocations = [:]
    def pageUrls = [:]

    Utils(WebDriver driver){
        this.driver = driver;
    }

    def static class RadioButtonConfig {
        def options = [:]

        def withOption(String choice, String id) {
            options.put(choice.toLowerCase(), id)
            return this
        }
    }

    def static toCamelCase(String s) {
        if (s.isEmpty()) return ""
        def words = s.tokenize(" ")*.toLowerCase()*.capitalize().join("")
        words[0].toLowerCase() + words.substring(1)
    }

    def static sendKeys(WebElement element, String v) {
        element.clear();
        if (v != null && v.length() != 0) {
            element.sendKeys(v);
        }
    }

    def static verifyHealthChecks(String... uris) {
        uris.each {
            try{
                def status = responseStatusFor("$it/health")
                assert status == 200 : "Healthcheck status; $status at $it/health."
            }catch (Exception e) {
                assert false: "Could not read healthcheck at $it/health. Exception: " + e.getMessage()
            }
        }
    }

    def static responseStatusFor(String url) {
        Response response = given()
            .get(url)
            .then().extract().response();

        return response.getStatusCode();
    }

    def clickRadioButton(RadioButtonConfig radioConfig, String value) {

        def choice = value.toLowerCase()

        if (radioConfig.options.containsKey(choice)) {

            String id = radioConfig.options.get(choice)

            By byCss = By.cssSelector("[id='$id'][type='radio']")
            driver.findElement(byCss).click()
        }
    }

   public void radioButton(String key){
       switch(key){
           case "Yes":
               driver.findElement(By.id("inLondon-yes-label")).click()
               break;

           case "No":
               driver.findElement(By.id("inLondon-no-label")).click()
               break;

           case "Main":
               driver.findElement(By.id("courseType-main-label")).click()
               break;

           case "Pre-sessional":
               driver.findElement(By.id("courseType-pre-sessional-label")).click()
               break;
       }
   }

    public void continuationCourse(String answer){
    if(answer == "No"){
        Thread.sleep(5000)
        driver.findElement(By.id("continuationCourse-no-label")).click()
    }
        if(answer == "Yes"){
            Thread.sleep(5000)
            driver.findElement(By.id("continuationCourse-yes-label")).click()
        }
    }

    def fillOrClearBySplitting( String key, String input, List<String> partNames, String delimiter) {

        if (input != null && input.length() != 0) {
            fillPartsBySplitting(key, input, delimiter, partNames)

        } else {
            clearParts(key, partNames)
        }
    }

    def fillPartsBySplitting( String key, String value, String delimiter, List<String> partNames) {

        String[] parts = value.split(delimiter)

        parts.eachWithIndex { part, index ->
            sendKeys(driver.findElement(By.id(key + partNames[index])), part)
        }
    }

    def clearParts( String key, List<String> partNames) {
        partNames.each { part ->
            driver.findElement(By.id(key + part)).clear()
        }
    }

    def assertCurrentPage(String location) {

        sleep(200)

        def expected = pageLocations[location]
        def actual = driver.currentUrl

       // assert actual.contains(expected): "Expected current page location to contain text: '$expected' but actual page location was '$actual' - Something probably went wrong earlier"
    }

    def verifyTableRowHeadersInOrder(DataTable expectedResult, tableId) {

        WebElement tableElement = driver.findElement(By.id(tableId))

        def entriesAsList = expectedResult.asList(String.class)

        entriesAsList.eachWithIndex { v, index ->
            def oneBasedIndex = index + 1;
            def result = tableElement.findElements(By.xpath(".//tbody/tr[$oneBasedIndex]/th[contains(., '$v')]"))
            assert result: "Could not find header [$v] for Results table row, [$oneBasedIndex] "
        }
    }

    def assertTextFieldEqualityForTable(DataTable expectedResult) {
        Map<String, String> entries = expectedResult.asMap(String.class, String.class)
        Thread.sleep(2000)
        assertTextFieldEqualityForMap(entries)
    }

    def assertTextFieldEqualityForMap(Map<String, String> entries) {

        entries.each { k, v ->
            String fieldName = toCamelCase(k);
            WebElement element = driver.findElement(By.id(fieldName))

            assert element.getText() == v
        }
    }

    def clickButton(){
        driver.findElement(By.className("button")).click()
    }

    def goToPage(pageName){
        def url = pageUrls[toCamelCase(pageName)]
        driver.get(url)
        assertCurrentPage(toCamelCase(pageName))
    }

    def verifyErrorSummaryText(DataTable expectedText) {
        List<String> errorSummaryTextItems = expectedText.asList(String.class)

        WebElement errorSummaryList = driver.findElement(By.id("error-summary-list"))
        def errorText = errorSummaryList.text

        errorSummaryTextItems.each {
            assert errorText.contains(it): "Error text did not contain: $it"
        }
    }
}
