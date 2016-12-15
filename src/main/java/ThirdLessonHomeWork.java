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
//1. Открыть главную страницу поисковой системы Bing.
        driver.navigate().to("http://www.bing.com");
//2. Перейти в раздел поиска изображений. Дождаться, что заголовок страницы имеет название “Лента изображений Bing.”
        By menuItemLocator = By.xpath(".//*[@id='scpl1' and text() = 'Images']");
        WebElement menuItemImage = driver.findElement(menuItemLocator);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(menuItemLocator));
        menuItemImage.click();
        String expectedTitle = "Bing Image Feed";
        wait.until(ExpectedConditions.titleIs(expectedTitle));

//3. Выполнить прокрутку страницы несколько раз.
// Каждый раз проверять, что при достижении низа страницы подгружаются новые блоки с изображениями.

        By locatorOfImages = By.xpath(".//div[@class ='iuscp varh']");
        List<WebElement> imageElementsBeforeScroll = driver.findElements(locatorOfImages);
        JavascriptExecutor javaScript = (JavascriptExecutor) driver;
        javaScript.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        List<WebElement> imageElementsAfterScroll = driver.findElements(locatorOfImages);
        verifyScroll(imageElementsBeforeScroll, imageElementsAfterScroll);
        javaScript.executeScript("window.scrollTo(0, 0)");

//4. В поисковую строку ввести слово без последней буквы (например “automatio” вместо “automation”).
// Дождаться появления слова целиком в выпадающем списке предложений.
// Выбрать искомое слово и дождаться загрузки результатов поискового запроса.

        By searchBoxFormLocator = By.cssSelector(".b_searchboxForm>input#sb_form_q");
        WebElement searchBoxForm = driver.findElement(searchBoxFormLocator);
        wait.until(ExpectedConditions.visibilityOf(searchBoxForm));
        searchBoxForm.sendKeys("automatio");

        WebElement completed = driver.findElement(By.xpath("//div[@class ='sa_tm' and text() = 'automatio'] /strong[text() = 'n']"));
        completed.click();

//5. Установить фильтр Дата: “В прошлом месяце”. Дождаться обновления результатов.
        driver.findElement(By.xpath(".//span[@class = 'ftrTB ftrP11' and text() = 'Date']")).click();
        driver.findElement(By.xpath(".//span[@class= 'ftrP12' and text() = 'Past month']")).click();
        By imagesLocator = By.xpath(".//div[@class='dg_u']");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(imagesLocator));
//6. Нажать на первое изображение из результатов поиска. Дождаться перехода в режим слайд шоу.
//        List<WebElement> images = driver.findElements(By.className("dg_u"));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("dg_u")));
        Actions mouse = new Actions(driver);
        By firstImageLocator = By.xpath(".//*[@id='dg_c']/div[1]/div/div[1]/div/a/img");
        mouse.moveToElement(driver.findElement(firstImageLocator)).build().perform();
        driver.findElement(firstImageLocator).click();

        driver.switchTo().frame("frame#OverlaylFrame.insightsOverlay");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("detail_film")));

        driver.quit();
    }




    private static void verifyScroll(List<WebElement> imageElementsBeforeScroll, List<WebElement> imageElementsAfterScroll) {
        if (imageElementsBeforeScroll.size() < imageElementsAfterScroll.size())
            System.out.println("Scroll pass");
        else
            System.out.println("Scroll fail");
    }

}
/*//div[@class='dg_u']//a[@href='/images/search?q=automation&view=detailv2&qft=+filterui%3aage-lt43200&id=C14D5148F195D5C6E7E91796084BF89E7E512E75&selectedIndex=0&ccid=BUGpBwXT&simid=608051698326242586&thid=OIP.M0541a90705d3f4468df5ab2bda47a472o0']
1. Открыть главную страницу поисковой системы Bing.
2. Перейти в раздел поиска изображений. Дождаться, что заголовок страницы имеет название “Лента изображений Bing.”
3. Выполнить прокрутку страницы несколько раз. Каждый раз проверять, что при достижении низа страницы подгружаются новые блоки с изображениями.
4. В поисковую строку ввести слово без последней буквы (например “automatio” вместо “automation”). Дождаться появления слова целиком в выпадающем списке предложений. Выбрать искомое слово и дождаться загрузки результатов поискового запроса.
5. Установить фильтр Дата: “В прошлом месяце”. Дождаться обновления результатов.
6. Нажать на первое изображение из результатов поиска. Дождаться перехода в режим слайд шоу.
7. Выполнить переключение на следующее, предыдущее изображение. После переключения между изображениями необходимо дожидаться обновления очереди изображений для показа в нижней части окна слайд шоу.
8. Нажать на отображаемое изображение в режиме слайд шоу и удостовериться, что картинка загрузилась в отдельной вкладке/окне.
 */