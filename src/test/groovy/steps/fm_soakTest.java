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
 * Created by mitchell on 06/12/16.
 */
public class fm_soakTest {

    static String fsUiRoot = "https://pttg-ip-fm-ui-test.notprod.homeoffice.gov.uk/";

    static long startTime;
    static long endTime;
    static long elapsedTime;
    static long currentTime;
    static long totalRunTime;
    static int fileLineCount;

    static String fileName = "fm_data.txt";
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

        Duration gap = Duration.ofSeconds(600);

        startTime = System.currentTimeMillis();
        totalRunTime = startTime + gap.toMillis();

        while (currentTime <= totalRunTime) {

            WebDriver driver = new ChromeDriver();
            currentTime = startTime + elapsedTime;
            elapsedTime = 0;

            driver.manage().deleteAllCookies();

            driver.get(fsUiRoot);

System.out.println(fileContent.get(count));

            if(driver.getCurrentUrl().contains("auth")) {
                driver.findElement(By.id("username")).sendKeys("pttg-test");
                driver.findElement(By.id("password")).sendKeys("pttg-test");
                driver.findElement(By.id("kc-login")).click();

            }

            driver.findElement(By.id("nino")).sendKeys(fileContent.get(count));
            driver.findElement(By.id("dependants")).sendKeys("1");
            driver.findElement(By.id("applicationRaisedDateDay")).sendKeys("15");
            driver.findElement(By.id("applicationRaisedDateMonth")).sendKeys("1");
            driver.findElement(By.id("applicationRaisedDateYear")).sendKeys("2015");
            driver.findElement(By.className("button")).click();
            try {
                Thread.sleep(1800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!driver.getCurrentUrl().contains("result")) {
                driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);

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
        }
        System.out.println(gap.toMillis());
    }

        }



