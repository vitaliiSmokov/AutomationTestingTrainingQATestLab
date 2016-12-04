import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

public class SecondLessonHomeWork {

    public static void main(String[] args) {

        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.bing.com/");
        WebElement searchBoxForm = driver.findElement(By.className("b_searchboxForm"));
        searchBoxForm.sendKeys("automation");
        driver.findElement(By.id("sb_form_go")).click();
        System.out.println("Page title: " + driver.getTitle());
        List <WebElement> searchResultTitles = driver.findElements(By.className("b_algo"));
        for (WebElement printResult:searchResultTitles){
            System.out.println(printResult.findElement(By.tagName("h2")).getText());
        }
        driver.quit();
    }
}
