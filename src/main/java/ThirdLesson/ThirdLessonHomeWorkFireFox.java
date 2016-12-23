package ThirdLesson;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ThirdLessonHomeWorkFireFox {
    public static void main(String[] args) {
        String pathToGeckoDriver = System.getProperty("user.dir") + "/src/main/resources/Drivers/geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", pathToGeckoDriver);

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
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locatorOfImages));
        List<WebElement> imageElementsBeforeScroll = driver.findElements(locatorOfImages);
        JavascriptExecutor javaScript = (JavascriptExecutor) driver;
        scrollTo(javaScript, "window.scrollTo(0, document.body.scrollHeight)");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locatorOfImages));
        List<WebElement> imageElementsAfterScroll = driver.findElements(locatorOfImages);
        verifyScrolling(imageElementsBeforeScroll, imageElementsAfterScroll);
        scrollTo(javaScript, "window.scrollTo(0, 0)");

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
        setTheDateFilterLastMonth(driver, wait);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(automationsImagesLocator));

//6. Нажать на первое изображение из результатов поиска. Дождаться перехода в режим слайд шоу.
////        Actions mouse = new Actions(driver);
//        By firstImageLocator = By.xpath(".//*[@id='dg_c']/div[1]/div/div[1]/div/a");
////        mouse.moveToElement(driver.findElement(firstImageLocator)).build().perform();
//        wait.until(ExpectedConditions.elementToBeClickable(firstImageLocator));
//        driver.findElement(firstImageLocator).click();

        By firstImageLocator = By.xpath("//div[@class='imgres']//img"); //div[@class='imgres']//img[@data-bm='18']
        Actions mouse = new Actions(driver);
        mouse.moveToElement(driver.findElement(By.xpath("//a[@class='b_logoArea']"))).build().perform();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ajaxMaskLayer")));
        driver.findElement(firstImageLocator).click();

        String iFrameId = "OverlayIFrame";
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id(iFrameId)));
        WebElement frame = driver.findElement(By.id(iFrameId));
        driver.switchTo().frame(frame);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("iol_navr")));

//7. Выполнить переключение на следующее, предыдущее изображение. После переключения между изображениями необходимо дожидаться обновления очереди изображений для показа в нижней части окна слайд шоу.
        WebElement nextImage = driver.findElement(By.id("iol_navr"));
        WebElement previousImage = driver.findElement(By.id("iol_navl"));
        nextImage.click();
        previousImage.click();


//8. Нажать на отображаемое изображение в режиме слайд шоу и удостовериться, что картинка загрузилась в отдельной вкладке/окне.





        driver.quit();
    }

    private static void scrollTo(JavascriptExecutor javaScript, String script) {
        javaScript.executeScript(script);
    }

    private static void goToImageSearch(WebDriver driver, WebDriverWait wait) {
        By menuItemLocator = By.xpath(".//*[@id='scpl1' and text() = 'Images']");
        WebElement menuItemImage = driver.findElement(menuItemLocator);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(menuItemLocator));
        menuItemImage.click();
    }

    private static void setTheDateFilterLastMonth(WebDriver driver, WebDriverWait wait) {
        By dateLocator = By.xpath("//span[@class = 'ftrTB ftrP11' and text() = 'Date']");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dateLocator));
        driver.findElement(dateLocator).click();
        By pastMonthLocator = By.xpath("//span[@class= 'ftrP12' and text() = 'Past month']");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(pastMonthLocator));
        driver.findElement(pastMonthLocator).click();
    }


    private static void verifyScrolling(List<WebElement> imageElementsBeforeScroll, List<WebElement> imageElementsAfterScroll) {
        if (imageElementsBeforeScroll.size() < imageElementsAfterScroll.size())
            System.out.println("Scroll pass");
        else
            System.out.println("Scroll fail");
    }

}