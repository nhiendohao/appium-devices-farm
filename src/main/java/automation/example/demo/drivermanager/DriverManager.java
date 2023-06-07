package automation.example.demo.drivermanager;

import org.openqa.selenium.WebDriver;

import automation.example.demo.drivermanager.appiumdriver.AppiumManager;
import automation.example.demo.drivermanager.appiumdriver.DesiredCapabilityBuilder;
import automation.example.demo.drivermanager.webdriver.WebDriverFactory;
import io.appium.java_client.AppiumDriver;

public class DriverManager {
    private static WebDriver driver;
    private static AppiumDriver appiumDriver;

    public static WebDriver getWebDriver() {
        final String browser = System.getProperty("webdriver.driver");
        driver = WebDriverFactory.startWebDriver(browser);
        return driver;
    }

    public static AppiumDriver getAppiumDriver() {
        final String deviceUDID = System.getProperty("appium.udid");
        if (appiumDriver == null) {
            final DesiredCapabilityBuilder builder = new DesiredCapabilityBuilder();
            appiumDriver = AppiumManager.startAppiumDriver(builder.buildDesiredCapabilities(deviceUDID));
        }
        return appiumDriver;
    }

    public static WebDriver getCurrentWebDriver() {
        return driver;
    }

    public static AppiumDriver getCurrentAppiumDriver() {
        return appiumDriver;
    }

}
