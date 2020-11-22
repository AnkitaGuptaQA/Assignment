import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Login {
    String CSV_path = "D:\\SevaDevelopment\\LoginDetails\\logindetails.csv";
    WebDriver driver;
    private CSVReader csvReader;
    String[] csvCells;

    @BeforeTest
    public void setup() throws Exception{
        WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.driver","D:\\SevaDevelopment\\ChromeDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://staging-ba4.izy.as/building-admin/");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void dataReadCSV() throws IOException, CsvValidationException{
        csvReader = new CSVReader(new FileReader(CSV_path));
        while ((csvCells = csvReader.readNext()) != null){
            System.out.println("Value1:["+csvCells[0]+"] /n Value2: ["+csvCells[1]+"]" );
            String emailusername = csvCells[0];
            String password = csvCells[1];
           By usernamepath = By.id("username");
           By passwordpath = By.id("password");
            driver.findElement(usernamepath).click();
            driver.findElement(usernamepath).clear();
            driver.findElement(usernamepath).sendKeys(emailusername);
            driver.findElement(passwordpath).click();
            driver.findElement(passwordpath).clear();
            driver.findElement(passwordpath).sendKeys(password);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.findElement(By.cssSelector("#loginForm > div.authentication-form-buttons > button")).click();

            WebDriverWait wait = new WebDriverWait(driver,10);
            WebElement logouticon;
            logouticon = driver.findElement(By.xpath("/html/body/app-root/admin-root/admin-toolbar/header/div[2]/ul/li[3]/a/i"));
            wait.until(ExpectedConditions.elementToBeClickable(logouticon)).click();

            driver.findElement(By.cssSelector("body > nico-modal > div > div > div > section.modal-footer > button:nth-child(2)")).click();


        }
    }

    @AfterTest
    public void exit() {
//
        driver.quit();
    }


}
