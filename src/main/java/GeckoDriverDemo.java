import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GeckoDriverDemo {

    public static void main(String[] args) {


    String pathToGeckoDriver = System.getProperty("user.dir") + "/src/main/resources/Drivers/geckodriver.exe";
    System.setProperty("webdriver.gecko.driver", pathToGeckoDriver);


    WebDriver driver = new FirefoxDriver();
    driver.navigate().to("http://www.bing.com");


    driver.quit();

    }
}
