package automation.example.demo.basetest;

import static automation.example.demo.drivermanager.appiumdriver.AppiumManager.startAppiumServer;
import static automation.example.demo.drivermanager.appiumdriver.AppiumManager.stopAppiumServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import automation.example.demo.drivermanager.DriverManager;
//import io.appium.java_client.AppiumDriver;

public class BaseTest {
    protected WebDriver driver;
//    protected AppiumDriver appiumDriver;

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
//        appiumDriver = DriverManager.getAppiumDriver();
        driver = DriverManager.getWebDriver();
    }

    @AfterEach
    public void afterTest() {
        driver.quit();
//        appiumDriver.quit();

    }

}
