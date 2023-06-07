package automation.example.demo.drivermanager.appiumdriver.models;

import java.awt.Point;

import org.openqa.selenium.WebDriver;

public class MobileScreen {
    private static MobileScreen mobileScreen;
    private final int width;
    private final int height;
    private final Point centerPoint;
    private final Point upPoint;
    private final Point downPoint;

    public MobileScreen(WebDriver driver) {
        this.width = driver.manage().window().getSize().getWidth();
        this.height = driver.manage().window().getSize().getHeight();
        this.centerPoint = calculateCenterPoint();
        this.downPoint = calculateDownPoint();
        this.upPoint = calculateUpPoint();
    }

    public static MobileScreen of(WebDriver driver) {
        if (mobileScreen == null) {
            synchronized (MobileScreen.class) {
                mobileScreen = new MobileScreen(driver);
            }
        }

        return mobileScreen;
    }

    private Point calculateCenterPoint() {
        return new Point(width / 2, height / 2);
    }

    private Point calculateUpPoint() {
        return new Point(width / 2, height / 4);
    }

    private Point calculateDownPoint() {
        return new Point(width / 2, (height * 3) / 4);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
