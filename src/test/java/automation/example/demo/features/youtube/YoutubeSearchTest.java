package automation.example.demo.features.youtube;

import automation.example.demo.drivermanager.appiumdriver.FilterOptions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import automation.example.demo.basetest.BaseTest;
import automation.example.demo.drivermanager.DriverManager;
import automation.example.demo.features.youtube.ui.pages.YoutubeHomePage;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import static automation.example.demo.drivermanager.appiumdriver.AppiumManager.waitForSessionEnded;

@Tags({
        @Tag("Regression"),
        @Tag("YoutubeSearch"),
})
public class YoutubeSearchTest extends BaseTest {
    private AppiumDriver appiumDriver;
    YoutubeHomePage youtubeHomePage;

    @BeforeEach
    public void beforeTest() {
        FilterOptions myFilter = new FilterOptions("", "", "android", "", false, true);
        appiumDriver = DriverManager.getMobileDriver(myFilter);
        youtubeHomePage = new YoutubeHomePage(appiumDriver);

    }

    @AfterEach
    public void afterTest() {
        String mySessionId = appiumDriver.getSessionId().toString();
        appiumDriver.quit();
        waitForSessionEnded(mySessionId, 20000);
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify corresponding result displays when searching for chicken")
    @Story("Youtube")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForChicken1() {

        youtubeHomePage.searchFor("hello vietnam");
        youtubeHomePage.chooseAnItemFromSuggestionList("hello vietnam");

    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify corresponding result displays when searching for chicken")
    @Story("Youtube")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForChicken2() {
        youtubeHomePage.searchFor("hello vietnam");
        youtubeHomePage.chooseAnItemFromSuggestionList("hello vietnam");

    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify corresponding result displays when searching for chicken")
    @Story("Youtube")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForChicken3() {
        youtubeHomePage.searchFor("hello vietnam");
        youtubeHomePage.chooseAnItemFromSuggestionList("hello vietnam");

    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify corresponding result displays when searching for chicken")
    @Story("Youtube")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForChicken4() {
        youtubeHomePage.searchFor("hello vietnam");
        youtubeHomePage.chooseAnItemFromSuggestionList("hello vietnam");

    }
}
