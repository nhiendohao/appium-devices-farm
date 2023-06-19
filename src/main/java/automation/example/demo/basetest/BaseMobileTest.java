package automation.example.demo.basetest;

import static automation.example.demo.drivermanager.appiumdriver.AppiumManager.startAppiumServer;
import static automation.example.demo.drivermanager.appiumdriver.AppiumManager.stopAppiumServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import helpers.AllureReportHelper;
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

    @AfterEach
    public void afterTest() {
        AllureReportHelper.attachScreenshot(appiumDriver);
        if (appiumDriver != null) {
            appiumDriver.quit();
        }
    }
}
