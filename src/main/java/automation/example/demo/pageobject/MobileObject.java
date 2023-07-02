package automation.example.demo.pageobject;

import static java.time.Duration.ofMillis;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.ContextAware;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;

import automation.example.demo.drivermanager.appiumdriver.models.Direction;
import automation.example.demo.drivermanager.appiumdriver.models.MobileScreen;
import io.appium.java_client.AppiumDriver;
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

    public void dragAndDropByGesture(WebElement sourceElement, WebElement targetElement) {
        Point source = sourceElement.getLocation();
        Point target = targetElement.getLocation();
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence dragNDrop = new Sequence(finger, 1);

        dragNDrop.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), source.x, source.y))
                 .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                 .addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), target.x, target.y))
                 .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        ((AppiumDriver) driver).perform(Arrays.asList(dragNDrop));
    }

    public void swipeWithDirectionByGesture(Direction direction, double edgeBorder) {
        MobileScreen mobileScreen = new MobileScreen(driver);
        final int height = mobileScreen.getHeight();
        final int width = mobileScreen.getWidth();
        final Point startPoint, endPoint;
        final int startPointX, startPointY, endPointX, endPointY;

        switch (direction) {
            case UP:
                startPointX = width / 2;
                startPointY = (int) (height * edgeBorder);
                endPointY = (int) (height * 0.90);

                startPoint = new Point(startPointX, startPointY);
                endPoint = new Point(startPoint.x, endPointY);
                break;
            case DOWN:
                startPointX = width / 2;
                startPointY = (int) (height * 0.90);
                endPointY = (int) (height * edgeBorder);

                startPoint = new Point(startPointX, startPointY);
                endPoint = new Point(startPoint.x, endPointY);
                break;
            case LEFT:
                startPointX = (int) (width * 0.90);
                startPointY = height / 2;
                endPointX = (int) (width * edgeBorder);

                startPoint = new Point(startPointX, startPointY);
                endPoint = new Point(endPointX, startPoint.y);
                break;
            case RIGHT:
                startPointX = (int) (width * edgeBorder);
                startPointY = height / 2;
                endPointX = (int) (width * edgeBorder);

                startPoint = new Point(startPointX, startPointY);
                endPoint = new Point(endPointX, startPoint.y);
                break;
            default:
                throw new IllegalArgumentException("DIRECTION: '" + direction + "' NOT supported");
        }

        final PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        final Sequence sequence = new Sequence(finger, 1);

        sequence.addAction(finger.createPointerMove(
                        ofMillis(0), PointerInput.Origin.viewport(), startPoint.x, startPoint.y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(
                        ofMillis(1000), PointerInput.Origin.viewport(), endPoint.x, endPoint.y))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        ((AppiumDriver) driver).perform(List.of(sequence));
    }

    public void swipeWithDirectionByGesture(Direction direction) {
        swipeWithDirectionByGesture(direction, 0.10);
    }

    public void swipeElementWithDirectionByGesture(WebElement element, Direction direction, int edgeBorder) {
        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();
        final Point startPoint = getMiddlePoint(element);
        final Point endPoint;

        switch (direction) {
            case UP:
                endPoint = new Point(startPoint.x, edgeBorder);
                break;
            case DOWN:
                final int endPointY = height - edgeBorder;
                endPoint = new Point(startPoint.x, endPointY);
                break;
            case LEFT:
                endPoint = new Point(edgeBorder, startPoint.y);
                break;
            case RIGHT:
                final int endPointX = width - edgeBorder;
                endPoint = new Point(endPointX, startPoint.y);
                break;
            default:
                throw new IllegalArgumentException("DIRECTION: '" + direction + "' NOT supported");
        }

        final PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        final Sequence sequence = new Sequence(finger, 1);

        sequence.addAction(finger.createPointerMove(
                        ofMillis(0), PointerInput.Origin.viewport(), startPoint.x, startPoint.y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(
                        ofMillis(1000), PointerInput.Origin.viewport(), endPoint.x, endPoint.y))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        ((AppiumDriver) driver).perform(List.of(sequence));
    }

    public void swipeElementWithDirectionByGesture(WebElement element, Direction direction) {
        swipeElementWithDirectionByGesture(element, direction, 10);
    }

    public Point getMiddlePoint(WebElement element) {
        final Point location = element.getLocation();
        final int width = element.getSize().getWidth();
        final int height = element.getSize().getHeight();
        return new Point(location.getX() + (width / 2), location.getY() + (height / 2));
    }

    public void switchToContext(String context) {
        ContextAware contextAware = (ContextAware) driver;
        Set<String> contextNames = contextAware.getContextHandles();
        System.out.println(contextNames);
        boolean success = false;
        int retries = 10;
        while (retries > 1 && !success) {
            WebDriver contextDriver = contextAware.context(context);
            if (contextDriver != null) {
                success = true;
            }
            waitForABit(2);
            retries--;
        }
    }

}
