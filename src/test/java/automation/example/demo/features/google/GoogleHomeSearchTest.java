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

@Tags({
        @Tag("Regression"),
        @Tag("GoogleSearch"),
})
@Feature("GoogleSearch")
public class GoogleHomeSearchTest extends BaseWebTest {
    GoogleHomePage googleHomePage;

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify corresponding result displays when searching for animal")
    @Story("Google Page")
    @ParameterizedTest
    @ValueSource(strings = { "Elephant", "Dog"})
    @Link(name = "TC-001", type = "mylink")
    @DisplayName("TC10011: google search")
    public void verifyCorrespondingResultDisplaysWhenSearchingForAnimal(String animal) {
        googleHomePage = new GoogleHomePage(driver);
        googleHomePage.openApplication();
        googleHomePage.searchFor("I like a " + animal);
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Description("Verify corresponding result displays when searching for fruit")
    @Story("Google Page")
    @Link(name = "TC-002", type = "mylink")
    @DisplayName("TC10012: google search")
    public void verifyCorrespondingResultDisplaysWhenSearchingForFruit() {
        googleHomePage = new GoogleHomePage(driver);
        googleHomePage.openApplication();
        googleHomePage.searchFor("I like a apple");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Description("Verify Google Logo")
    @Story("Google Page")
    @Link(name = "TC-003", type = "mylink")
    @DisplayName("TC10013: google search")
    public void verifyGoogleLogo() {
        googleHomePage = new GoogleHomePage(driver);
        googleHomePage.openApplication();
        googleHomePage.verifyGoogleLogo();
    }

}
