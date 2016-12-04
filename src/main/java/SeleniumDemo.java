import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumDemo {

    public static void main(String[] args) {

        String pathToChromeDriver = System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe";

        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.bing.com/");


        driver.quit();
    }

}
