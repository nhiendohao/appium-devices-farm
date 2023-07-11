package automation.example.demo.features.google;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import automation.example.demo.drivermanager.DriverManager;
import automation.example.demo.features.search.ui.pages.GooglePage;
import helpers.AllureReportHelpers;
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
public class GoogleSearchTest {
    private WebDriver driver;
    GooglePage googlePage;

    @BeforeEach
    public void beforeTest() {
        driver = DriverManager.getWebDriver();
        googlePage = new GooglePage(driver);
    }

    @AfterEach
    public void afterTest() {
        AllureReportHelpers.attachScreenshot(driver);
        DriverManager.quitWebDriver();
    }

    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TC10014")
    @Link(name = "GoogleLink", url = "https://www.googgle.com")
    @Story("Search for animal")
    @DisplayName("TC10014: google search")
    @Description("Verify corresponding result displays when searching for animal")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForAnimal() {
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
        googlePage.openApplication();
        googlePage.verifyGoogleLogo();
    }

}
