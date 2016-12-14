import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDemo {

    public static void main(String[] args) {

        String pathToChromeDriver = System.getProperty("user.dir") + "/src/main/resources/Drivers/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.bing.com/");
        driver.quit();
    }
}
