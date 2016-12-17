import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ThirdLessonHomeWork {
    public static void main(String[] args) {

        WebDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
//1. Открыть главную страницу поисковой системы Bing.
        driver.navigate().to("http://www.bing.com");
//2. Перейти в раздел поиска изображений. Дождаться, что заголовок страницы имеет название “Лента изображений Bing.”
        goToImageSearch(driver, wait);
        String expectedTitle = "Bing Image Feed";
        wait.until(ExpectedConditions.titleIs(expectedTitle));

//3. Выполнить прокрутку страницы несколько раз.
// Каждый раз проверять, что при достижении низа страницы подгружаются новые блоки с изображениями.

        By locatorOfImages = By.xpath(".//div[@class ='iuscp varh']");
        List<WebElement> imageElementsBeforeScroll = driver.findElements(locatorOfImages);
        JavascriptExecutor javaScript = (JavascriptExecutor) driver;
        javaScript.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        List<WebElement> imageElementsAfterScroll = driver.findElements(locatorOfImages);
        verifyScrolling(imageElementsBeforeScroll, imageElementsAfterScroll);
        javaScript.executeScript("window.scrollTo(0, 0)");

//4. В поисковую строку ввести слово без последней буквы (например “automatio” вместо “automation”).
        By searchBoxFormLocator = By.cssSelector(".b_searchboxForm>input#sb_form_q");
        WebElement searchBoxForm = driver.findElement(searchBoxFormLocator);
        wait.until(ExpectedConditions.visibilityOf(searchBoxForm));
        searchBoxForm.sendKeys("automatio");
        By automationLocator = By.xpath("//div[@class = 'sa_tm']/strong[text() = 'n']");
        // Дождаться появления слова целиком в выпадающем списке предложений.
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(automationLocator));
        // Выбрать искомое слово и дождаться загрузки результатов поискового запроса.
        driver.findElement(automationLocator).click();
        By automationsImagesLocator = By.xpath("//div[@class = 'dg_u']");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(automationsImagesLocator));


//5. Установить фильтр Дата: “В прошлом месяце”. Дождаться обновления результатов.
        setTheDateFilterLastMonth(driver);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(automationsImagesLocator));

//6. Нажать на первое изображение из результатов поиска. Дождаться перехода в режим слайд шоу.
//        Actions mouse = new Actions(driver);
        By firstImageLocator = By.xpath(".//*[@id='dg_c']/div[1]/div/div[1]/div/a/img");
//        mouse.moveToElement(driver.findElement(firstImageLocator)).build().perform();
        driver.findElement(firstImageLocator).click();

        String iFrameId = "OverlayIFrame";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(iFrameId)));
        driver.switchTo().frame(iFrameId);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("detail_film")));

//7. Выполнить переключение на следующее, предыдущее изображение. После переключения между изображениями необходимо дожидаться обновления очереди изображений для показа в нижней части окна слайд шоу.
        WebElement nextImage = driver.findElement(By.id("iol_navr"));
        WebElement previousImage = driver.findElement(By.id("iol_navl"));
        nextImage.click();
        previousImage.click();


//8. Нажать на отображаемое изображение в режиме слайд шоу и удостовериться, что картинка загрузилась в отдельной вкладке/окне.





        driver.quit();
    }

    private static void goToImageSearch(WebDriver driver, WebDriverWait wait) {
        By menuItemLocator = By.xpath(".//*[@id='scpl1' and text() = 'Images']");
        WebElement menuItemImage = driver.findElement(menuItemLocator);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(menuItemLocator));
        menuItemImage.click();
    }

    private static void setTheDateFilterLastMonth(WebDriver driver) {
        driver.findElement(By.xpath(".//span[@class = 'ftrTB ftrP11' and text() = 'Date']")).click();
        driver.findElement(By.xpath(".//span[@class= 'ftrP12' and text() = 'Past month']")).click();
    }


    private static void verifyScrolling(List<WebElement> imageElementsBeforeScroll, List<WebElement> imageElementsAfterScroll) {
        if (imageElementsBeforeScroll.size() < imageElementsAfterScroll.size())
            System.out.println("Scroll pass");
        else
            System.out.println("Scroll fail");
    }

}