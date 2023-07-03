package automation.example.demo.basetest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import automation.example.demo.drivermanager.DriverManager;
import helpers.AllureReportHelpers;

public class BaseWebTest {
    protected WebDriver driver;

    @BeforeEach
    public void beforeTest() {
        driver = DriverManager.getWebDriver();
    }

    @AfterEach
    public void afterTest() {
        AllureReportHelpers.attachScreenshot(driver);
        DriverManager.quitWebDriver();
    }

}
