package automation.example.demo.basetest;

import static automation.example.demo.drivermanager.appiumdriver.AppiumManager.startAppiumServer;
import static automation.example.demo.drivermanager.appiumdriver.AppiumManager.stopAppiumServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import automation.example.demo.drivermanager.DriverManager;
import io.appium.java_client.AppiumDriver;

public class BaseMobileTest {
    protected AppiumDriver appiumDriver;

    @BeforeAll
    public static void setup() {
        startAppiumServer();
    }

    @AfterAll
    public static void tearDown() {
        stopAppiumServer();
    }

    @BeforeEach
    public void beforeTest() {
        final String deviceUDID = System.getProperty("appium.udid");
        appiumDriver = DriverManager.getMobileDriver(deviceUDID);
    }

    @AfterEach
    public void afterTest() {
        appiumDriver.quit();
    }
}
