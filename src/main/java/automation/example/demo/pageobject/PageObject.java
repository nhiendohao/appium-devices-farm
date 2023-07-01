package automation.example.demo.pageobject;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class PageObject {
    protected WebDriver driver;

    public PageObject (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebDriver getCurrentDriver() {
        if (driver == null) {
            throw new NullPointerException("Driver is null");
        }
        return driver;
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    public void clickOn(WebElement element) {
        element.click();
    }

    public void clickOn(By by) {
        WebElement element = driver.findElement(by);
        element.click();
    }

    public void enter(WebElement element, String value) {
        element.sendKeys(value);
    }

    public void enter(By by, String value) {
        WebElement element = driver.findElement(by);
        element.sendKeys(value);
    }

    public void enterKey(Keys keyEvent) {
        Actions actions = new Actions(driver);
        actions.sendKeys(keyEvent)
               .perform();
    }

    public boolean isElementPresent(By by) {
        WebElement element = driver.findElement(by);
        return element.isDisplayed();
    }

    public void waitForABit(int seconds){
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isElementPresent(WebElement element) {
        return element.isDisplayed();
    }

    public void waitForAWhile (int timeOut) {
        try {
            Thread.sleep(1000L * timeOut);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    public String getText(By by) {
        WebElement element = driver.findElement(by);
        return element.getText();
    }


    public void waitUntilElementVisible(WebElement element, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitUntilElementVisible(By element, int timeOut) {
        WebElement webElement = driver.findElement(element);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public void waitUntilElementInvisible(WebElement element, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitUntilElementInvisible(By element, int timeOut) {
        WebElement webElement = driver.findElement(element);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    public void waitUntilElementClickable(WebElement element, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Explicit wait is wait for specific element loaded with condition, it is local wait.
     */
    public void waitUntilElementClickable(By element, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.elementToBeClickable((element)));
    }

    /**
     * Implicit wait is wait for elements loaded, it is global wait
     */
    public boolean isElementPresent(WebElement element, int timeOut) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOut));
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException ex) {
            System.out.println("Element is not present.");
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        }
    }

    public boolean isElementNotPresent(By element, int timeOut) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOut));
        try {
            int size = driver.findElements(element).size();
            return size < 1;
        } catch (NoSuchElementException ex) {
            System.out.println("Element is not present.");
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        }
    }

}
