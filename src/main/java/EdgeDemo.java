import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class EdgeDemo {
    public static void main(String[] args) {

        String pathToEdgeDriver = System.getProperty("user.dir") + "/src/main/resources/Drivers/MicrosoftWebDriver.exe";
        System.setProperty("webdriver.edge.driver", pathToEdgeDriver);

        WebDriver edgeDriver = new EdgeDriver();
        edgeDriver.get("https://www.bing.com/");
        edgeDriver.quit();
    }
}

