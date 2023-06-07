package automation.example.demo.basetest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import automation.example.demo.drivermanager.DriverManager;

public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = DriverManager.getWebDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}
