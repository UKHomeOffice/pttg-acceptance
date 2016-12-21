package steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by mitchell on 08/12/16.
 */
public class fs_soakTest {

    static String fsUiRoot = "https://pttg-fs-ui-test.notprod.homeoffice.gov.uk/";

    static long startTime;
    static long endTime;
    static long elapsedTime;
    static long currentTime;
    static long totalRunTime;
    static int fileLineCount;

    static String fileName = "fs_data.txt";
    static ArrayList<String> fileContent = new ArrayList<>() ;
    static String line = null;

    public static void main(String[] args) {
        int count = 0;
        File fl = new File(fileName);
        try {
            FileReader fr = new FileReader(fl);
            BufferedReader br = new BufferedReader(fr);
            while((line = br.readLine().toString()) != null){
                fileContent.add(line);
                fileLineCount++;
                System.out.print("file content :  "+line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        Duration gap = Duration.ofSeconds(1600);

        startTime = System.currentTimeMillis();
        totalRunTime = startTime + gap.toMillis();

        fileLineCount = fileLineCount - 1;

        while (currentTime <= totalRunTime) {

            WebDriver driver = new ChromeDriver();
            currentTime = startTime + elapsedTime;
            elapsedTime = 0;

            driver.manage().deleteAllCookies();

            driver.get(fsUiRoot);
            driver.manage().window().maximize();

            System.out.println(fileContent.get(count));

            if(driver.getCurrentUrl().contains("auth")) {
                driver.findElement(By.id("username")).sendKeys("pttg-test");
                driver.findElement(By.id("password")).sendKeys("pttg-test");
                driver.findElement(By.id("kc-login")).click();
            }
            String[] listContent = fileContent.get(count).split(",");
            String[] sortCodePart = listContent[1].split("-");
            driver.findElement(By.id("student-type-0-label")).click();
            driver.findElement(By.className("button")).click();
            driver.findElement(By.id("endDateDay")).sendKeys("1");
            driver.findElement(By.id("endDateMonth")).sendKeys("1");
            driver.findElement(By.id("endDateYear")).sendKeys("2016");

            driver.findElement(By.id("dobDay")).sendKeys("1");
            driver.findElement(By.id("dobMonth")).sendKeys("1");
            driver.findElement(By.id("dobYear")).sendKeys("1996");

            driver.findElement(By.id("numberOfDependants")).sendKeys("0");
            driver.findElement(By.id("sortCodePart1")).sendKeys(sortCodePart[0]);
            driver.findElement(By.id("sortCodePart2")).sendKeys(sortCodePart[1]);
            driver.findElement(By.id("sortCodePart3")).sendKeys(sortCodePart[2]);
            driver.findElement(By.id("accountNumber")).sendKeys(listContent[0]);
            driver.findElement(By.id("inLondon-0-label")).click();
            driver.findElement(By.id("courseStartDateDay")).sendKeys("1");
            driver.findElement((By.id("courseStartDateMonth"))).sendKeys("1");
            driver.findElement(By.id("courseStartDateYear")).sendKeys("2016");
            driver.findElement(By.id("courseEndDateDay")).sendKeys("30");
            driver.findElement(By.id("courseEndDateMonth")).sendKeys("3");
            driver.findElement(By.id("courseEndDateYear")).sendKeys("2016");
            driver.findElement(By.id("continuationEndDateDay")).sendKeys("30");
            driver.findElement(By.id("continuationEndDateMonth")).sendKeys("4");
            driver.findElement(By.id("continuationEndDateYear")).sendKeys("2016");
            driver.findElement(By.id("totalTuitionFees")).sendKeys("0");
            driver.findElement(By.id("tuitionFeesAlreadyPaid")).sendKeys("0");
            driver.findElement(By.id("accommodationFeesAlreadyPaid")).sendKeys("0");
            driver.findElement(By.className("button")).click();
            try {
                Thread.sleep(1800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!driver.getCurrentUrl().contains("result")) {
                driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

            }
            endTime = System.currentTimeMillis();


            elapsedTime = endTime - startTime;
            System.out.println("elapsed time   :"+ elapsedTime);
            System.out.println("Running time is:   "+ currentTime);
            driver.quit();
            count++;
            if(fileLineCount == count){
                count = 0;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(gap.toMillis());
    }

}
