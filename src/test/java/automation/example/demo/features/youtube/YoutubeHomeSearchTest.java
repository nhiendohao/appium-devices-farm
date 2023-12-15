package automation.example.demo.features.youtube;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import automation.example.demo.drivermanager.appiumdriver.FilterOptions;
import io.appium.java_client.AppiumDriver;

import automation.example.demo.basetest.BaseTest;
import automation.example.demo.drivermanager.DriverManager;
import automation.example.demo.features.youtube.ui.pages.YoutubeHomePage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import static automation.example.demo.drivermanager.appiumdriver.AppiumManager.waitForSessionEnded;

@Tags({
        @Tag("Regression"),
        @Tag("YoutubeSearch"),
})
public class YoutubeHomeSearchTest extends BaseTest {
    AppiumDriver  appiumDriver;

    YoutubeHomePage youtubeHomePage;

    @BeforeEach
    public void beforeTest() {
//        FilterOptions myFilter = new FilterOptions("", "", "android", "", true, true);
//        appiumDriver = DriverManager.getMobileDriver(myFilter);
//        youtubeHomePage = new YoutubeHomePage(appiumDriver);

    }

    @AfterEach
    public void afterTest() {
        String mySessionId = appiumDriver.getSessionId().toString();
        appiumDriver.quit();
        waitForSessionEnded(mySessionId,20000);
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify corresponding result displays when searching for chicken")
    @Story("Youtube")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForChicken() {
        FilterOptions myFilter = new FilterOptions("", "", "android", "", true, true);
        appiumDriver = DriverManager.getMobileDriver(myFilter);
        youtubeHomePage = new YoutubeHomePage(appiumDriver);

        youtubeHomePage.searchFor("hello vietnam");
        youtubeHomePage.chooseAnItemFromSuggestionList("hello vietnam");

    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify corresponding result displays when searching for chicken")
    @Story("Youtube")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForChicken1() {
        FilterOptions myFilter = new FilterOptions("", "", "android", "", true, true);
        appiumDriver = DriverManager.getMobileDriver(myFilter);
        youtubeHomePage = new YoutubeHomePage(appiumDriver);
        youtubeHomePage.searchFor("hello vietnam");
        youtubeHomePage.chooseAnItemFromSuggestionList("hello vietnam");
    }
}
