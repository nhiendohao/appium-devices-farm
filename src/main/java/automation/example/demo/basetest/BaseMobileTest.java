package automation.example.demo.basetest;

import static automation.example.demo.drivermanager.appiumdriver.AppiumManager.startAppiumServer;
import static automation.example.demo.drivermanager.appiumdriver.AppiumManager.stopAppiumServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import automation.example.demo.drivermanager.DriverManager;
import helpers.AllureReportHelpers;
import io.appium.java_client.AppiumDriver;

public class BaseMobileTest {
    protected AppiumDriver appiumDriver;

    @BeforeAll
    public static void setup() {
        startAppiumServer();
    }

    @AfterAll
    public static void tearDown() throws InterruptedException {
        /**
         * Add a delay 3 seconds before stopping Appium server
         */
        Thread.sleep(3000);
        stopAppiumServer();
    }

    @AfterEach
    public void afterTest() {
        AllureReportHelpers.attachScreenshot(appiumDriver);
        DriverManager.quitMobileDriver();
    }
}
