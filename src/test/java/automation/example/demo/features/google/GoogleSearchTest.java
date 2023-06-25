package automation.example.demo.features.google;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import automation.example.demo.basetest.BaseWebTest;
import automation.example.demo.features.search.ui.pages.GooglePage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;

@Tags({
        @Tag("Regression"),
        @Tag("GoogleSearch"),
})
@Feature("GoogleSearch")
public class GoogleSearchTest extends BaseWebTest {
    GooglePage googlePage;

    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TC10014")
    @Link(name = "GoogleLink", url = "https://www.googgle.com")
    @Story("Search for animal")
    @DisplayName("TC10014: google search")
    @Description("Verify corresponding result displays when searching for animal")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForAnimal() {
        googlePage = new GooglePage(driver);
        googlePage.openApplication();
        googlePage.searchFor("I like a dog");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Search for animal")
    @TmsLink("TC10015")
    @Link(name = "GoogleLink", url = "https://www.googgle.com")
    @Story("Search for fruit")
    @DisplayName("TC10015: google search")
    @Description("Verify corresponding result displays when searching for fruit")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForFruit() {
        googlePage = new GooglePage(driver);
        googlePage.openApplication();
        googlePage.searchFor("I like a banana");
    }

    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TC10013")
    @Link(name = "GoogleLink", url = "https://www.googgle.com")
    @Story("Search for animal")
    @DisplayName("TC10016: google search")
    @Description("Verify Google Logo")
    @Test
    public void verifyGoogleLogo() {
        googlePage = new GooglePage(driver);
        googlePage.openApplication();
        googlePage.verifyGoogleLogo();
    }

}
