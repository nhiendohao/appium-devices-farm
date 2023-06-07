package automation.example.demo.pageobject;

import static io.appium.java_client.touch.WaitOptions.waitOptions;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.ElementOption;

public class MobileObject extends PageObject{
    protected WebDriver driver;

    public MobileObject(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void moveToElementAndClick(WebElement element) {
        final Actions actions = new Actions(driver);
        actions.moveToElement(element)
               .click()
               .build()
               .perform();
    }

    public void moveToElementAndClick(WebElement element, int xOffset, int yOffset) {
        final Actions actions = new Actions(driver);
        actions.moveToElement(element, xOffset, yOffset)
               .click()
               .build()
               .perform();
    }

    public void moveToElement(WebElement source, WebElement destination) {
        final TouchAction actions = new TouchAction((AppiumDriver) driver);
        actions.press(ElementOption.element(source))
               .waitAction(waitOptions(Duration.ofSeconds(30)))
               .moveTo(ElementOption.element(destination))
               .release()
               .perform();
    }

    public void switchToWebView(String webView) {
        final Set<String> contextNames = ((AppiumDriver) driver).getContextHandles();
        System.out.println("contextName: " + contextNames);

        for (String contextName : contextNames) {
            if (contextName.equals(webView)) {
                ((AppiumDriver) driver).context(webView);
                return;
            }
        }
    }
}
