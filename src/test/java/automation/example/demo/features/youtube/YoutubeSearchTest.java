package automation.example.demo.features.youtube;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import automation.example.demo.basetest.BaseTest;
import automation.example.demo.drivermanager.DriverManager;
import automation.example.demo.features.youtube.ui.pages.YoutubeHomePage;
import helpers.AllureReportHelpers;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Tags({
        @Tag("Regression"),
        @Tag("YoutubeSearch"),
})
public class YoutubeSearchTest extends BaseTest {
    private WebDriver driver;
    YoutubeHomePage youtubeHomePage;

    @BeforeEach
    public void beforeTest() {
        final String deviceUDID = "R5CT20VRX5Y";
        driver = DriverManager.getMobileDriver(deviceUDID);
        youtubeHomePage = new YoutubeHomePage(driver);
    }

    @AfterEach
    public void afterTest() {
        AllureReportHelpers.attachScreenshot(driver);
        DriverManager.quitMobileDriver();
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify corresponding result displays when searching for U17")
    @Story("Youtube")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForU17() {
        youtubeHomePage.searchFor("u17 viet nam");
        youtubeHomePage.chooseAnItemFromSuggestionList(1);
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify corresponding result displays when searching for U23")
    @Story("Youtube")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForU23() {
        youtubeHomePage.searchFor("u23 viet nam");
        youtubeHomePage.chooseAnItemFromSuggestionList(1);
    }
}
