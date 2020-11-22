import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Configuration {
    public static void main(String[] args){


        System.setProperty("webdriver.chrome.driver","D:\\SevaDevelopment\\ChromeDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://staging-ba4.izy.as/building-admin/");


        driver.quit();
    }
}
