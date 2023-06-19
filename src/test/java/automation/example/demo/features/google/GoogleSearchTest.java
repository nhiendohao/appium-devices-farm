package automation.example.demo.features.google;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import automation.example.demo.basetest.BaseWebTest;
import automation.example.demo.features.search.ui.pages.GooglePage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Tags({
        @Tag("Regression"),
        @Tag("GoogleSearch"),
})
public class GoogleSearchTest extends BaseWebTest {
    GooglePage googlePage;

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Description("Verify corresponding result displays when searching for animal")
    @Story("Google Page")
    public void verifyCorrespondingResultDisplaysWhenSearchingForAnimal() {
        googlePage = new GooglePage(driver);
        googlePage.openApplication();
        googlePage.searchFor("I like a dog");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Description("Verify corresponding result displays when searching for fruit")
    @Story("Google Page")
    public void verifyCorrespondingResultDisplaysWhenSearchingForFruit() {
        googlePage = new GooglePage(driver);
        googlePage.openApplication();
        googlePage.searchFor("I like a banana");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Description("Verify Google Logo")
    @Story("Google Page")
    public void verifyGoogleLogo() {
        googlePage = new GooglePage(driver);
        googlePage.openApplication();
        googlePage.verifyGoogleLogo();
    }

}