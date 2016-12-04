import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumDemo {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");


        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.bing.com/");
//        By by = By.className("b_searchbox");
//        WebElement element = driver.findElement(by);

        driver.quit();
    }

}
