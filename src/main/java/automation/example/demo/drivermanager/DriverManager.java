package automation.example.demo.drivermanager;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.NoSuchDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;

import net.bytebuddy.implementation.bytecode.Throw;

import automation.example.demo.drivermanager.appiumdriver.AppiumManager;
import automation.example.demo.drivermanager.appiumdriver.DesiredCapabilityBuilder;
import automation.example.demo.drivermanager.webdriver.WebDriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public class DriverManager {

    public static WebDriver getWebDriver() {
        String browser = System.getProperty("webdriver.driver");
        return WebDriverFactory.startWebDriver(browser);
    }

    public static AppiumDriver getMobileDriver(String deviceUdid) {
        ;
        final DesiredCapabilityBuilder builder = new DesiredCapabilityBuilder();
        return AppiumManager.startAppiumDriver(builder.buildDesiredCapabilities(deviceUdid));
    }

    public static Platform getMobilePlatform(WebDriver driver) {
        try {
            Platform platform = ((RemoteWebDriver) driver).getCapabilities().getPlatformName();
            if (Platform.ANDROID.equals(platform)) {
                return Platform.ANDROID;
            } else if (Platform.IOS.equals(platform)){
                return Platform.IOS;
            } else {
                return Platform.MAC;
            }
        } catch (Exception ex) {
            throw new NoSuchDriverException("Unknown driver");
        }
    }

}
