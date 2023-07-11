package automation.example.demo.basetest;

import static automation.example.demo.drivermanager.appiumdriver.AppiumManager.startAppiumServer;
import static automation.example.demo.drivermanager.appiumdriver.AppiumManager.stopAppiumServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    public static void setup() {
        startAppiumServer();
    }

    @AfterAll
    public static void tearDown() {
        stopAppiumServer();
    }
}
