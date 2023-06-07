package automation.example.demo.drivermanager;

import org.openqa.selenium.WebDriver;

import automation.example.demo.drivermanager.appiumdriver.AppiumManager;
import automation.example.demo.drivermanager.appiumdriver.DesiredCapabilityBuilder;
import automation.example.demo.drivermanager.webdriver.WebDriverFactory;
import io.appium.java_client.AppiumDriver;

public class DriverManager {

    public static WebDriver getWebDriver() {
        String browser = System.getProperty("webdriver.driver");
        return WebDriverFactory.startWebDriver(browser);
    }

    public static AppiumDriver getMobileDriver(String deviceUdid) {;
        final DesiredCapabilityBuilder builder = new DesiredCapabilityBuilder();
        return AppiumManager.startAppiumDriver(builder.buildDesiredCapabilities(deviceUdid));
    }
}
