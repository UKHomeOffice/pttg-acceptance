package steps

import cucumber.api.DataTable
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.Given
import cucumber.api.java.en.When
import net.thucydides.core.annotations.Managed
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
/**
 * @Author Home Office Digital
 */
class FinancialStatusSteps {

    //def fsUiRoot = "http://localhost:8001"
   // def fsUiRoot = "http://mitchell-Inspiron-15-3552:8001"
    def fsUiRoot = "https://pttg-fs-ui-test.notprod.homeoffice.gov.uk/"
    def delay = 500

    @Managed


    WebDriver driver
   // WebDriver driver = new ChromeDriver()

    Utils utils

    @Before
    def setUp() {
        utils = new Utils(driver)
        driver.sleep(3000)
        driver.manage().deleteAllCookies()
        utils.pageLocations = [
                'studentType'       : '#/financial-status-student-type',
                'doctorateQuery'    : '#/financial-status-query-doctorate',
                'non-doctorateQuery': '#/financial-status-query-non-doctorate',
                'accountNotFound'   : '#/financial-status-no-record',
                'calc'       : '#/financial-status-student-type-calc'
        ]

        utils.pageUrls = [
                'studentType'       : fsUiRoot,
                'doctorateQuery'    : fsUiRoot + '#/financial-status-query-doctorate',
                'non-doctorateQuery': fsUiRoot + '#/financial-status-query-non-doctorate',
                'calc': fsUiRoot + '#/financial-status-calc'
        ]

    }

    @After
    def tearDown(){
        driver.manage().deleteAllCookies()
    }


    def sortCodeParts = ["Part1", "Part2", "Part3"]
    def sortCodeDelimiter = "-"

    def dateParts = ["Day", "Month", "Year"]
    def dateDelimiter = "/"

    def inLondonRadio = new Utils.RadioButtonConfig()
            .withOption('yes', 'inLondon-0-label')
            .withOption('no', 'inLondon-1-label')

    def studentTypeRadio = new Utils.RadioButtonConfig()
        .withOption('non-doctorate', 'student-type-0-label')
        .withOption('doctorate', 'student-type-1')
        .withOption('pgdd', 'student-type-2')
        .withOption('sso', 'student-type-3')

          //  .withOption('non-doctorate', 'student-type-1')
            //.withOption('doctorate', 'student-type-2')

    def studentType


    def submitEntries(Map<String, String> entries) {
        entries.each { k, v ->
            String key = Utils.toCamelCase(k)

            if (key.endsWith("Date") || key =="dob") {
                utils.fillOrClearBySplitting(key, v, dateParts, dateDelimiter)

            } else if (key == "sortCode") {
                utils.fillOrClearBySplitting(key, v, sortCodeParts, sortCodeDelimiter)

            } else {
                def element = driver.findElement(By.id(key))

                if (key == "inLondon") {
                   // utils.clickRadioButton(inLondonRadio, v)
                    utils.radioButton(v)

                }
               else if(key == "continuationCourse"){
                  utils.continuationCourse(v)
                }
                else if (key == "courseType") {
                    utils.radioButton(v)
                } else {
                    Utils.sendKeys(element, v)
                }
            }
        }

        sleep(delay)
        driver.findElement(By.className("button")).click()
        driver.findElement(By.className("button")).getText().equals()
    }

    def chooseAndSubmitStudentType(String type) {
        studentType(driver,type)
        Thread.sleep(1000)
        submitStudentTypeChoice()
    }

    def selectStudentType(String type) {
        utils.clickRadioButton(studentTypeRadio, type)
    }

    def submitStudentTypeChoice() {
        Thread.sleep(1000)
        utils.clickButton()

    }

    public void studentType(WebDriver driver,String tableValue){
        String id = "";

        switch(tableValue){
            case "general-student":
                id = "applicant-type-nondoctorate-label"
                break;

                case "DES":
                    id = "applicant-type-doctorate-label"
                 break;

                case "pgdd":
                    id = "applicant-type-pgdd-label"
                break;

                case "ssuo":
                    id = "applicant-type-sso-label"
                break;
            case "t2main":
                id = "applicant-type-t2main-label"
                break;

                default:
                    id = ""
        }
        driver.findElement(By.id(id)).click()
    }

    @Given("^(?:caseworker|user) is using the financial status service ui\$")
    def user_is_using_the_financial_status_service_ui() throws Throwable {
        driver.manage().deleteAllCookies()
        driver.navigate().refresh();
        driver.manage().window().maximize()
        driver.get(fsUiRoot)
        utils.assertCurrentPage('studentType')
    }

    @Given("^caseworker is using the financial status service calc\$")
    public void caseworker_is_using_the_financial_status_service_calc()  {
        driver.get(driver.currentUrl + "-calc")
        utils.assertCurrentPage('calc')
    }

    @Given("^the (.*) student type is chosen\$")
    def the_student_type_is_chosen(String type) {
       studentType(driver,type,)
        studentType = type
        chooseAndSubmitStudentType(type)
    }

    @Given("^the caseworker has logged into key cloak using the following\$")
    public void the_caseworker_has_logged_into_key_cloak_using_the_following(DataTable entries)  {
        Map<String, String> field = entries.asMap(String.class, String.class)

        driver.manage().deleteAllCookies()
        if(driver.currentUrl.contains("auth")) {
             driver.findElement(By.id("username")).sendKeys(field.get("User name"))
             driver.findElement(By.id("password")).sendKeys(field.get("Password"))
             driver.findElement(By.id("kc-login")).click()
   // driver.findElement(By.className("text")).click()
   // driver.findElement(By.id("cred_userid_inputtext")).sendKeys(field.get("User name"))
    //driver.findElement(By.id("cred_password_inputtext")).sendKeys(field.get("Password"))
  //  driver.sleep(3000)
   // driver.findElement(By.id("cred_sign_in_button")).click()


    //println " " + "xxxxxxx"+  entries.raw().get(0).get(1)
}
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
}
