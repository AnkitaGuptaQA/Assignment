import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Page {
    String CSV_path = System.getProperty("user.dir")  + "/src/main/resources/public/logindetails.csv";
    public WebDriver driver;
    private CSVReader csvReader;
    String[] csvCells;

    //Launch the browser with the mentioned URL
    @BeforeTest
    public void setup() throws Exception{
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://demo.nopcommerce.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        String pageTitle;
        pageTitle = driver.findElement(By.cssSelector("body > div.master-wrapper-page > div.master-wrapper-content > div > div > div > div > div.topic-block > div.topic-block-title > h2")).getText();
        Assert.assertEquals("Welcome to our store",pageTitle);
        System.out.println(pageTitle);
    }

    // Register the users
    @Test (priority = 1)
    public void dataReadCSV() throws IOException, CsvValidationException, InterruptedException {

        csvReader = new CSVReader(new FileReader(CSV_path));
        while ((csvCells = csvReader.readNext()) != null){
            Thread.sleep(5000);
            // Navigate to the Register page
            driver.findElement(By.cssSelector("body > div.master-wrapper-page > div.header > div.header-upper > div.header-links-wrapper > div.header-links > ul > li:nth-child(1) > a")).click();
            String registertitle;
            registertitle = driver.findElement(By.xpath("/html/body/div[6]/div[3]/div/div/div/div[1]/h1")).getText();
            Assert.assertEquals("Register",registertitle);
            //System.out.println("Value1:["+csvCells[0].getClass().getName()+"] /n Value2: ["+csvCells[1]+"] /n Value3: ["+csvCells[2]+"]" );
            String registergender = csvCells[0];
            String registerfirstname = csvCells[1];
            String registerlastname = csvCells[2];
            String registerdate  = csvCells[3];
            String registermonth = csvCells[4];
            String registeryear  = csvCells[5];
            String registeremail  = csvCells[6];
            String registercompanyname  = csvCells[7];
            String registerpassword  = csvCells[8];
            String registerconfirmpassword  = csvCells[9];
           By firstnamepath = By.id("FirstName");
           By lastnamepath = By.id("LastName");
           By emailpath = By.id("Email");
           By companynamepath = By.id("Company");
           By passwordpath = By.id("Password");
           By confirmpasswordpath = By.id("ConfirmPassword");

           // Assert the title of the personal details
            String personaldetails;
            personaldetails = driver.findElement(By.cssSelector("body > div.master-wrapper-page > div.master-wrapper-content > div > div > div > div.page-body > form > div:nth-child(1) > div.title > strong")).getText();
            Assert.assertEquals("Your Personal Details",personaldetails);

           // select the gender
            Thread.sleep(5000);
           if (registergender.equals("F")){
              WebElement female =  driver.findElement(By.id("gender-female"));
              female.click();
           }
           else {
               driver.findElement(By.cssSelector("#gender-male")).click();
           }
           System.out.println(registergender);
          driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

          // Add first name
            driver.findElement(firstnamepath).click();
            driver.findElement(firstnamepath).clear();
            driver.findElement(firstnamepath).sendKeys(registerfirstname);

            //Add Last name
            driver.findElement(lastnamepath).click();
            driver.findElement(lastnamepath).clear();
            driver.findElement(lastnamepath).sendKeys(registerlastname);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            //Select date of birth
            Select date = new Select(driver.findElement(By.name("DateOfBirthDay")));
            date.selectByValue(registerdate);
            System.out.println(registerdate);
            Select month = new Select(driver.findElement(By.name("DateOfBirthMonth")));
            month.selectByValue(registermonth);
            System.out.println(registermonth);
            Select year = new Select(driver.findElement(By.name("DateOfBirthYear")));
            year.selectByValue(registeryear);
            System.out.println(registeryear);

            //Add email address
            WebDriverWait wait = new WebDriverWait(driver,10);
            wait.until(ExpectedConditions.elementToBeClickable(emailpath)).click();
            driver.findElement(emailpath).clear();
            driver.findElement(emailpath).sendKeys(registeremail);

            //scroll the screen
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

            //Add company name
            wait.until(ExpectedConditions.elementToBeClickable(companynamepath)).click();
            driver.findElement(companynamepath).clear();
            driver.findElement(companynamepath).sendKeys(registercompanyname);

            //Add password
            wait.until(ExpectedConditions.elementToBeClickable(passwordpath)).click();
            driver.findElement(passwordpath).clear();
            driver.findElement(passwordpath).sendKeys(registerpassword);

            //Add confirm password
            wait.until(ExpectedConditions.elementToBeClickable(confirmpasswordpath)).click();
            driver.findElement(confirmpasswordpath).clear();
            driver.findElement(confirmpasswordpath).sendKeys(registerconfirmpassword);

            //Click on register button
            driver.findElement(By.id("register-button")).click();

            //Click on the button to continue
            driver.findElement(By.name("register-continue")).click();
            Thread.sleep(5000);

            //logout
            WebElement logouttext;
            logouttext = driver.findElement(By.xpath("/html/body/div[6]/div[1]/div[1]/div[2]/div[1]/ul/li[2]/a"));
            wait.until(ExpectedConditions.elementToBeClickable(logouttext)).click();


        }
    }

    @Test(priority = 2)
    public void loginthroughCSV() throws IOException, CsvValidationException, InterruptedException {

        csvReader = new CSVReader(new FileReader(CSV_path));
        Thread.sleep(5000);
        while ((csvCells = csvReader.readNext()) != null) {
            Thread.sleep(5000);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            // Navigate to the login page
            driver.findElement(By.cssSelector("body > div.master-wrapper-page > div.header > div.header-upper > div.header-links-wrapper > div.header-links > ul > li:nth-child(2) > a")).click();
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

            String registeredemail = csvCells[6];
            String registeredpassword = csvCells[8];


            //Add email
            driver.findElement(By.cssSelector("#Email")).click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.findElement(By.cssSelector("#Email")).clear();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.findElement(By.cssSelector("#Email")).sendKeys(registeredemail);
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

            //Add password
            driver.findElement(By.cssSelector("#Password")).click();
            driver.findElement(By.cssSelector("#Password")).clear();
            driver.findElement(By.cssSelector("#Password")).sendKeys(registeredpassword);
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

            //Navigated to the login page
            driver.findElement(By.cssSelector("body > div.master-wrapper-page > div.master-wrapper-content > div > div > div > div.page-body > div.customer-blocks > div.returning-wrapper.fieldset > form > div.buttons > input")).click();
            Thread.sleep(5000);

            //logout

            driver.findElement(By.xpath("/html/body/div[6]/div[1]/div[1]/div[2]/div[1]/ul/li[2]/a")).click();
            Thread.sleep(5000);

        }
    }



    @AfterTest
    public void exit() {
        driver.quit();
    }


}
