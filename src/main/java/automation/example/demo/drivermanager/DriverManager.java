package automation.example.demo.drivermanager;

import org.openqa.selenium.WebDriver;

import automation.example.demo.drivermanager.webdriver.WebDriverFactory;

public class DriverManager {
    private static WebDriver driver;

    public static WebDriver getWebDriver() {
        driver = WebDriverFactory.startWebDriver();
        return driver;
    }

    public static WebDriver getCurrentWebDriver() {
        return driver;
    }

    public static void quitWebDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
