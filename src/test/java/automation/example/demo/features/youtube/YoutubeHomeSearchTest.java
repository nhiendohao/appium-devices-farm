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
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Tags({
        @Tag("Regression"),
        @Tag("YoutubeSearch"),
})
public class YoutubeHomeSearchTest extends BaseTest {
    private WebDriver driver;
    YoutubeHomePage youtubeHomePage;

    @BeforeEach
    public void beforeTest() {
        final String deviceUDID = "QV780HN49H";
        driver = DriverManager.getMobileDriver(deviceUDID);
        youtubeHomePage = new YoutubeHomePage(driver);
    }

    @AfterEach
    public void afterTest() {
        AllureReportHelpers.attachScreenshot(driver);
        DriverManager.quitMobileDriver();
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify corresponding result displays when searching for chicken")
    @Story("Youtube")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForChicken() {
        youtubeHomePage.searchFor("chicken");
        youtubeHomePage.chooseAnItemFromSuggestionList(1);
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify corresponding result displays when searching for dog")
    @Story("Youtube")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForDog() {
        youtubeHomePage.searchFor("Dog");
        youtubeHomePage.chooseAnItemFromSuggestionList(1);
    }
}
