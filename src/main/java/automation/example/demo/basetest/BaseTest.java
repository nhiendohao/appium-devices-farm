package automation.example.demo.basetest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import automation.example.demo.drivermanager.webdriver.WebDriverFactory;

public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = WebDriverFactory.getWebDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
