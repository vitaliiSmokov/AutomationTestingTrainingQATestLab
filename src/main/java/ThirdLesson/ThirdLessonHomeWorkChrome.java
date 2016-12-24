package ThirdLesson;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.EOFException;
import java.util.List;
import java.util.Set;

public class ThirdLessonHomeWorkChrome {
    public static void main(String[] args) {
        String pathToChromeDriver = System.getProperty("user.dir") + "/src/main/resources/Drivers/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);

        WebDriver driver = new ChromeDriver();
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
        List<WebElement> imageElementsAfterScroll = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(locatorOfImages, imageElementsBeforeScroll.size()));
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
        setTheDateFilterLastMonth(driver);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(automationsImagesLocator));

//6. Нажать на первое изображение из результатов поиска. Дождаться перехода в режим слайд шоу.
//        Actions mouse = new Actions(driver);
        By firstImageLocator = By.xpath(".//*[@id='dg_c']/div[1]/div/div[1]/div/a");
//        mouse.moveToElement(driver.findElement(firstImageLocator)).build().perform();
        wait.until(ExpectedConditions.elementToBeClickable(firstImageLocator));
        driver.findElement(firstImageLocator).click();

        String iFrameId = "OverlayIFrame";
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(iFrameId)));
        WebElement frame = driver.findElement(By.id(iFrameId));
        driver.switchTo().frame(frame);
        By mainImageLocator = By.xpath("//img[@class='mainImage accessible nofocus']");
        wait.until(ExpectedConditions.presenceOfElementLocated(mainImageLocator));

//7. Выполнить переключение на следующее, предыдущее изображение.
// После переключения между изображениями необходимо дожидаться обновления очереди изображений для показа в нижней части окна слайд шоу.
        WebElement nextImage = driver.findElement(By.id("iol_navr"));
        WebElement previousImage = driver.findElement(By.id("iol_navl"));
        List<WebElement> detailFilm = driver.findElements(By.xpath("//a[@class='iol_fst']"));
        nextImage.click();
        //verify queue of the images
        wait.until(ExpectedConditions.visibilityOf(detailFilm.get(7)));
        previousImage.click();
//8. Нажать на отображаемое изображение в режиме слайд шоу и удостовериться, что картинка загрузилась в отдельной вкладке/окне.
        WebElement mainImage = driver.findElement(mainImageLocator);
        String parentHandle = driver.getWindowHandle();
        final Set<String> oldWindowHandles = driver.getWindowHandles();
        mainImage.click();
        String newWindowHandle = getNewWindowHandle(driver, oldWindowHandles);
        driver.switchTo().window(newWindowHandle);
        if (driver.findElement(By.tagName("img")).isDisplayed())
            System.out.println("Image is present in new tab");
        driver.switchTo().window(parentHandle);

        driver.quit();
    }

    private static String getNewWindowHandle(WebDriver driver, final Set<String> oldWindowHandles) {
        return (new WebDriverWait(driver, 10)).until(new ExpectedCondition<String>() {
                public String apply(WebDriver webDriver) {
                    Set<String> newWindowHandles = webDriver.getWindowHandles();
                    newWindowHandles.removeAll(oldWindowHandles);
                    return newWindowHandles.iterator().next();
                }
            });
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