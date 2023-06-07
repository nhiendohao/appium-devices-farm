package automation.example.demo.pageobject;

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

    public boolean isElementPresent(WebElement element) {
        return element.isDisplayed();
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    public String getText(By by) {
        WebElement element = driver.findElement(by);
        return element.getText();
    }

    public void waitUntilElementVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitUntilElementVisible(WebElement element, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitUntilElementVisible(By element, int timeOut) {
        WebElement webElement = driver.findElement(element);
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public void waitUntilElementInvisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitUntilElementInvisible(WebElement element, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitUntilElementInvisible(By element, int timeOut) {
        WebElement webElement = driver.findElement(element);
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    public void waitUntilElementClickable(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitUntilElementClickable(WebDriver driver, By element) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable((element)));
    }

}
