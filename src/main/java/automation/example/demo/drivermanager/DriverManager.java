package automation.example.demo.drivermanager;

import static automation.example.demo.constants.Constants.DEVICE_WAIT_TINE;
import static automation.example.demo.drivermanager.appiumdriver.Devices.*;
import static automation.example.demo.pageobject.PageObject.waitForABit;

import java.util.List;

import automation.example.demo.drivermanager.appiumdriver.Device;
import automation.example.demo.drivermanager.appiumdriver.FilterOptions;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.NoSuchDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;

import automation.example.demo.drivermanager.appiumdriver.AppiumManager;
import automation.example.demo.drivermanager.appiumdriver.DesiredCapabilityBuilder;
import automation.example.demo.drivermanager.webdriver.WebDriverFactory;
import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverManager {
    private static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private static final ThreadLocal<AppiumDriver> mobileDriver = new ThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);


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
        int retries = 10;
        boolean success = false;
        while (retries < 11 && !success) {
            if (mobileDriver.get().getSessionId() == null) {
                success = true;
            }
            waitForABit(3);
            retries--;
        }
        mobileDriver.set(null);
    }

    /**
     * Retrieves an AppiumDriver instance for mobile automation based on the provided filter options.
     * This method ensures that a connected mobile device matching the specified filter is available on the Appium server.
     *
     * @param myFilter The filter options specifying the desired mobile device characteristics.
     * @return An AppiumDriver instance configured for the selected mobile device.
     * @throws RuntimeException If no connected devices on the Appium server match the given filter or
     *                          if there is no available mobile device in the given devices list.
     *                          Also, may throw other runtime exceptions related to Appium initialization.
     */
    public static synchronized AppiumDriver getMobileDriver(FilterOptions myFilter) {
        final DesiredCapabilityBuilder builder = new DesiredCapabilityBuilder();
        waitForAvailableDevice(myFilter.getPlatform(), DEVICE_WAIT_TINE);

        List<Device> filteredDevices = filterActiveDevicesByOption(myFilter);
        String deviceUdid = "N/A";
        String devicePlatform = "Unknown";
        String deviceName = "Unknown";
        String platformVersion = "Unknown";

        if (filteredDevices.size() == 0) {
            throw new RuntimeException("No connected devices on the Appium server match the given filter");
        }

        for (Device device : filteredDevices) {
            if (isDeviceAvailable(device.udid)) {
                logger.info("Device {} is available", device.udid);
                deviceUdid = device.udid;
                devicePlatform = device.platform;
                deviceName = device.name;
                platformVersion = device.sdk;
                logger.info("Current Device UDID is {} and Platform is {} deviceName is {} platformVersion is {}", deviceUdid, devicePlatform, deviceName, platformVersion);
                break;
            }
        }

        if (deviceUdid.equals("N/A")) {
            throw new RuntimeException("No available mobile on the Appium server in the given devices list");
        }

        logger.info("Starting a new session with UDID {}", deviceUdid);

        mobileDriver.set(AppiumManager.startAppiumDriver(
                builder.buildDesiredCapabilitiesByOptions(devicePlatform, deviceUdid, deviceName, platformVersion)));

        return mobileDriver.get();
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
