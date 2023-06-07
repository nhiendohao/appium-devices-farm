package automation.example.demo.features.ui;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import automation.example.demo.basetest.BaseTest;
import automation.example.demo.features.search.ui.pages.GoogleHomePage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Tags({
        @Tag("Regression"),
        @Tag("GoogleHomeSearch"),
})
public class GoogleHomeSearchTest extends BaseTest {
    GoogleHomePage googleHomePage;

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Description("Verify corresponding result displays when searching for animal")
    @Story("Google Page")
    public void verifyCorrespondingResultDisplaysWhenSearchingForAnimal() {
        googleHomePage = new GoogleHomePage(driver);
        googleHomePage.openApplication();
        googleHomePage.searchFor("I like a elephant");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Description("Verify corresponding result displays when searching for fruit")
    @Story("Google Page")
    public void verifyCorrespondingResultDisplaysWhenSearchingForFruit() {
        googleHomePage = new GoogleHomePage(driver);
        googleHomePage.openApplication();
        googleHomePage.searchFor("I like a apple");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Description("Verify Google Logo")
    @Story("Google Page")
    public void verifyGoogleLogo() {
        googleHomePage = new GoogleHomePage(driver);
        googleHomePage.openApplication();
        googleHomePage.verifyGoogleLogo();
    }

}
