package automation.example.demo.drivermanager;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.NoSuchDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;

import automation.example.demo.drivermanager.appiumdriver.AppiumManager;
import automation.example.demo.drivermanager.appiumdriver.DesiredCapabilityBuilder;
import automation.example.demo.drivermanager.webdriver.WebDriverFactory;
import io.appium.java_client.AppiumDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private static final ThreadLocal<AppiumDriver> mobileDriver = new ThreadLocal<>();

    public static WebDriver getWebDriver() {
        String browser = System.getProperty("webdriver.driver");
        webDriver.set(WebDriverFactory.startWebDriver(browser));
        return webDriver.get();
    }

    public static AppiumDriver getMobileDriver(String deviceUdid) {
        final DesiredCapabilityBuilder builder = new DesiredCapabilityBuilder();
        mobileDriver.set(AppiumManager.startAppiumDriver(
                builder.buildDesiredCapabilities(deviceUdid)));
        return mobileDriver.get();
    }

    public static WebDriver getCurrentWebDriver() {
        return webDriver.get();
    }

    public static AppiumDriver getCurrentMobileDriver() {
        return mobileDriver.get();
    }

    public static void quitWebDriver() {
        webDriver.get().quit();
        webDriver.set(null);
    }

    public static void quitMobileDriver() {
        mobileDriver.get().quit();
        mobileDriver.set(null);
    }

    public static Platform getMobilePlatform(WebDriver driver) {
        try {
            Platform platform = ((RemoteWebDriver) driver).getCapabilities().getPlatformName();
            if (platform.equals(Platform.ANDROID)) {
                return Platform.ANDROID;
            } else if (platform.equals(Platform.IOS)) {
                return Platform.IOS;
            } else {
                return Platform.MAC;
            }
        } catch (NoSuchDriverException ex) {
            ex.printStackTrace();
            throw new NoSuchDriverException("Unknown driver");
        }
    }

}
