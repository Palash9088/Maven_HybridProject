package Base;//import java.util.*;

import Constant.ConstantPaths;
import com.google.common.io.Files;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PredefinedActions {
    static WebDriver driver;
    static WebDriverWait wait;
    static Logger log = Logger.getLogger(PredefinedActions.class);

    public static void openBrowser(String url, String browser) {
        log.trace("STEP -> Opening Browser");
        switch (browser.toUpperCase()) {
            case "CHROME" -> {
                System.setProperty("webdriver.chrome.driver", ConstantPaths.CHROMEDRIVER_WIN);
                driver = new ChromeDriver();
            }
            case "FIREFOX" -> {
                System.setProperty("webdriver.gecko.driver", ConstantPaths.FIREFOXDRIVER_WIN);
                driver = new FirefoxDriver();
            }
            case "EDGE" -> {
                System.setProperty("webdriver.edge.driver", ConstantPaths.EDGEDRIVER_WIN);
                driver = new EdgeDriver();
            }
            default -> log.trace("Wrong keyword");
        }
        driver.manage().window().maximize();
        log.trace("STEP -> Opening Given " + url);
        driver.get(url);
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
    }

    protected void hoverOnElement(WebElement target, boolean isWaitRequired) {
        Actions actions = new Actions(driver);
        if (isWaitRequired) {
            wait.until(ExpectedConditions.visibilityOf(target));
            actions.moveToElement(target).build().perform();
        } else {
            actions.moveToElement(target).build().perform();
        }
        log.trace("User able to Hover on element");
    }

    protected void switchToFrameByElement(WebElement element) {
        driver.switchTo().frame(element);
        log.trace("User able to switched to Frame");
    }

    protected String getMainWindowHandleId() {
        log.trace("User waiting to get Window Handle Id");
        return driver.getWindowHandle();
    }

    protected Set<String> getAllWindowHandlesId() {
        log.trace("User waiting for all Window handle Ids");
        return driver.getWindowHandles();
    }

    protected void switchToSpecificWindow(String windowId) {
        driver.switchTo().window(windowId);
        log.trace("User Switched to given Frame");

    }

    protected void clickOnElement(WebElement element) {
        element.click();
        log.trace("User able to click on Element");
    }

    protected void clickOnElement(String locator, boolean isWaitRequired) {
        WebElement element = getElement(locator, isWaitRequired);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        log.trace("User able to click on Elements");
    }

    protected void enterText(WebElement element, String text) {
        if (element.isEnabled())
            element.sendKeys(text);
        else element.sendKeys(text);
        log.trace("User able to send text on Element");
    }

    protected void enterText(String locator, boolean isWaitRequired, String textToBe) {
        WebElement element = getElement(locator, isWaitRequired);
        enterText(element, textToBe);
        log.trace("User able to send text on Element");
    }

    protected List<WebElement> getListOfWebElements(String locator, boolean isWaitRequired) {
        String locatorType = getLocatorType(locator);
        String locatorValue = getLocatorValue(locator);
        log.trace("User waiting for List of WebElements");
        return driver.findElements(getByReference(locatorType, locatorValue));
    }

    protected List<String> getElementListText(String locator, boolean isWaitRequired) {
        //By byLocator = getByReference(getLocatorType(locator), getLocatorValue(locator));
        List<WebElement> elementList = getListOfWebElements(locator, isWaitRequired);
        List<String> elementListText = new ArrayList<>();
        for (WebElement element : elementList) {
            elementListText.add(element.getText());
        }
        log.trace("User Waiting for List of Text from web elements");
        return elementListText;
    }

    protected List<Integer> getElementsListInNumberFormat(String locator, boolean isWaitRequired) {
        List<WebElement> elementList = getListOfWebElements(locator, isWaitRequired);
        List<Integer> elementListText = new ArrayList<>();
        for (WebElement element : elementList) {
            elementListText.add(Integer.parseInt(element.getText()));
        }
        log.trace("User Waiting for List of Text from web elements");
        return elementListText;
    }

    protected String getElementText(String locator, boolean isWaitRequired) {
        WebElement element = getElement(locator, isWaitRequired);
        log.trace("User waiting for Element text");
        return element.getText();
    }

    private String getLocatorType(String locator) {
        return locator.split("]:-")[0].substring(1);
    }

    private String getLocatorValue(String locator) {

        return locator.split("]:-")[1];
    }

    private By getByReference(String locatorType, String locatorValue) {
        switch (locatorType.toUpperCase()) {
            case "XPATH":
                return By.xpath(locatorValue);
            case "ID":
                return By.id(locatorValue);
            case "CLASSNAME":
                return By.className(locatorValue);
            case "PARTIALLINK":
                return By.partialLinkText(locatorValue);
            case "LINKTEXT":
                return By.linkText(locatorValue);
            case "CSS":
                return By.cssSelector(locatorValue);
            case "TAGNAME":
                return By.tagName(locatorValue);
            case "NAME":
                return By.name(locatorValue);
            default:
                System.out.println("Invalid Locator Type");
        }
        return null;
    }

    protected WebElement getElement(String locator, boolean isWaitRequired) {
        WebElement element = null;
        String locatorType = getLocatorType(locator);
        String locatorValue = getLocatorValue(locator);
        if (isWaitRequired)
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(getByReference(locatorType, locatorValue)));
        else
            driver.findElement(getByReference(locatorType, locatorValue));
        log.trace("User waiting for element");

        /*switch (locatorType.toUpperCase()) {
            case "XPATH":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
                else
                    driver.findElement(By.xpath(locatorValue));
                break;
            case "ID":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
                else
                    driver.findElement(By.id(locatorValue));
                break;
            case "CLASSNAME":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
                else
                    driver.findElement(By.className(locatorValue));
                break;
            case "LINKTEXT":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
                else
                    driver.findElement(By.linkText(locatorValue));
                break;
            case "PARTIAL LINKTEXT":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
                else
                    driver.findElement(By.partialLinkText(locatorValue));
                break;
            case "CSS":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
                else
                    driver.findElement(By.cssSelector(locatorValue));
                break;
            default:
                System.out.println("Invalid Locator");
        }*/
        return element;
    }

    protected String getAttribute(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }

    protected void javaScriptClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
        log.trace("User able to click on Element using JS");
    }

    protected void openNewTab() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open();");
        log.trace("User able to open new tab using JS");
    }

    protected void navigateTo(String url) {
        driver.navigate().to(url);
        log.trace("User able to navigate to given Url -> " + url);
    }

    public static void takeScreenshot(String filename) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(src, new File(ConstantPaths.SCREENSHOT_PATH + filename + ConstantPaths.SCREENSHOT_EXTENSION));
        } catch (IOException e) {
            log.error("Did not capture screenshot" + e.getMessage());
            throw new RuntimeException();
        }
    }


    public static void closeBrowser() {
        driver.close();
        log.trace("User successfully close the browser");
    }

    public static void quitBrowser() {
        driver.close();
        log.trace("User successfully quit the browser");
    }
}
