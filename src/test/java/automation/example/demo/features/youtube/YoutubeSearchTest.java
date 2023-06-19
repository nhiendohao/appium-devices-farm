package automation.example.demo.features.youtube;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import automation.example.demo.basetest.BaseMobileTest;
import automation.example.demo.drivermanager.DriverManager;
import automation.example.demo.features.search.ui.pages.GooglePage;
import automation.example.demo.features.youtube.ui.pages.YoutubeHomePage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Tags({
        @Tag("Regression"),
        @Tag("YoutubeSearch"),
})
public class YoutubeSearchTest extends BaseMobileTest {

    @BeforeEach
    public void beforeTest() {
        final String deviceUDID = "R5CT20VRX5Y";
        appiumDriver = DriverManager.getMobileDriver(deviceUDID);
    }

    YoutubeHomePage youtubeHomePage;

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Description("Verify corresponding result displays when searching for U17")
    @Story("Youtube")
    public void verifyCorrespondingResultDisplaysWhenSearchingForU17() {
        youtubeHomePage = new YoutubeHomePage(appiumDriver);
        youtubeHomePage.searchFor("u17 viet nam");
        youtubeHomePage.chooseAnItemFromSuggestionList(1);
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Description("Verify corresponding result displays when searching for U23")
    @Story("Youtube")
    public void verifyCorrespondingResultDisplaysWhenSearchingForU23() {
        youtubeHomePage = new YoutubeHomePage(appiumDriver);
        youtubeHomePage.searchFor("u23 viet nam");
        youtubeHomePage.chooseAnItemFromSuggestionList(1);
    }
}
