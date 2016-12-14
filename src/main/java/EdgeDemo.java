import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class EdgeDemo {
    public static void main(String[] args) {

        String pathToEdgeDriver = System.getProperty("user.dir") + "/src/main/resources/Drivers/MicrosoftWebDriver.exe";
        System.setProperty("webdriver.edge.driver", pathToEdgeDriver);

        WebDriver edgeDriver = new EdgeDriver();
        edgeDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        edgeDriver.get("https://www.bing.com");

        WebDriverWait wait = new WebDriverWait(edgeDriver,5);
        By searchBoxFormLocator = By.cssSelector(".b_searchboxForm>input#sb_form_q");
        WebElement searchBoxForm = edgeDriver.findElement(searchBoxFormLocator);
        wait.until(ExpectedConditions.visibilityOf(searchBoxForm));
        searchBoxForm.sendKeys("automatio");

        edgeDriver.quit();
    }
}

