package automation.example.demo.features.youtube;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import automation.example.demo.basetest.BaseMobileTest;
import automation.example.demo.drivermanager.DriverManager;
import automation.example.demo.features.youtube.ui.pages.YoutubeHomePage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Tags({
        @Tag("Regression"),
        @Tag("YoutubeSearch"),
})
public class YoutubeHomeSearchTest extends BaseMobileTest {

    @BeforeEach
    public void beforeTest() {
        final String deviceUDID = "QV780HN49H";
        appiumDriver = DriverManager.getMobileDriver(deviceUDID);
    }

    YoutubeHomePage youtubeHomePage;

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify corresponding result displays when searching for chicken")
    @Story("Youtube")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForChicken() {
        youtubeHomePage = new YoutubeHomePage(appiumDriver);
        youtubeHomePage.searchFor("chicken");
        youtubeHomePage.chooseAnItemFromSuggestionList(1);
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify corresponding result displays when searching for dog")
    @Story("Youtube")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForDog() {
        youtubeHomePage = new YoutubeHomePage(appiumDriver);
        youtubeHomePage.searchFor("Dog");
        youtubeHomePage.chooseAnItemFromSuggestionList(1);
    }
}
