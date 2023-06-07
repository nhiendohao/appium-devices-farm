package automation.example.demo.features.ui;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import automation.example.demo.basetest.BaseWebTest;
import automation.example.demo.features.search.ui.pages.GoogleHomePage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Tags({
        @Tag("Regression"),
        @Tag("GoogleHomeSearch"),
})
public class GoogleHomeSearchTest extends BaseWebTest {
    GoogleHomePage googleHomePage;

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify corresponding result displays when searching for animal")
    @Story("Google Page")
    @ParameterizedTest
    @ValueSource(strings = { "Elephant", "Dog"})
    public void verifyCorrespondingResultDisplaysWhenSearchingForAnimal(String animal) {
        googleHomePage = new GoogleHomePage(driver);
        googleHomePage.openApplication();
        googleHomePage.searchFor("I like a " + animal);
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
