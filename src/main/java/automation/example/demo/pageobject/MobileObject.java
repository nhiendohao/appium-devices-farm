package automation.example.demo.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MobileObject extends PageObject{

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
}
