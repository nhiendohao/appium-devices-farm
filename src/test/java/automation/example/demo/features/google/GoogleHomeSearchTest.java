package automation.example.demo.features.google;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import automation.example.demo.basetest.BaseWebTest;
import automation.example.demo.features.search.ui.pages.GoogleHomePage;
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
public class GoogleHomeSearchTest extends BaseWebTest {
    GoogleHomePage googleHomePage;

    @Severity(SeverityLevel.NORMAL)
    @ParameterizedTest
    @ValueSource(strings = { "Elephant", "Dog"})
    @Link(name = "Google Link", url = "https://www.googgle.com")
    @TmsLink("TC10011")
    @Story("Search for animal")
    @DisplayName("TC10011: google search")
    @Description("Verify corresponding result displays when searching for animal")
    public void verifyCorrespondingResultDisplaysWhenSearchingForAnimal(String animal) {
        googleHomePage = new GoogleHomePage(driver);
        googleHomePage.openApplication();
        googleHomePage.searchFor("I like a " + animal);
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @TmsLink("TC10012")
    @Link(name = "Google Link", url = "https://www.googgle.com")
    @Story("Search for fruit")
    @DisplayName("TC10012: google search")
    @Description("Verify corresponding result displays when searching for fruit")
    public void verifyCorrespondingResultDisplaysWhenSearchingForFruit() {
        googleHomePage = new GoogleHomePage(driver);
        googleHomePage.openApplication();
        googleHomePage.searchFor("I like a apple");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @TmsLink("TC10013")
    @Link(name = "Google Link", url = "https://www.googgle.com")
    @DisplayName("TC10013: google search")
    @Description("Verify Google Logo")
    public void verifyGoogleLogo() {
        googleHomePage = new GoogleHomePage(driver);
        googleHomePage.openApplication();
        googleHomePage.verifyGoogleLogo();
    }

}
