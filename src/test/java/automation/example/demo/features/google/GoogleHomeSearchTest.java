package automation.example.demo.features.google;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;

import automation.example.demo.drivermanager.DriverManager;
import automation.example.demo.features.search.ui.pages.GoogleHomePage;
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
public class GoogleHomeSearchTest {
    private WebDriver driver;
    GoogleHomePage googleHomePage;

    @BeforeEach
    public void beforeTest() {
        driver = DriverManager.getWebDriver();
        googleHomePage = new GoogleHomePage(driver);
    }

    @AfterEach
    public void afterTest() {
        AllureReportHelpers.attachScreenshot(driver);
        DriverManager.quitWebDriver();
    }

    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Google Link", url = "https://www.googgle.com")
    @TmsLink("TC10011")
    @Story("Search for animal")
    @DisplayName("TC10011: google search")
    @Description("Verify corresponding result displays when searching for animal")
    @ParameterizedTest
    @ValueSource(strings = { "Elephant", "Dog"})
    public void verifyCorrespondingResultDisplaysWhenSearchingForAnimal(String animal) {
        googleHomePage.openApplication();
        googleHomePage.searchFor("I like a " + animal);
    }

    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TC10012")
    @Link(name = "Google Link", url = "https://www.googgle.com")
    @Story("Search for fruit")
    @DisplayName("TC10012: google search")
    @Description("Verify corresponding result displays when searching for fruit")
    @Test
    public void verifyCorrespondingResultDisplaysWhenSearchingForFruit() {
        googleHomePage.openApplication();
        googleHomePage.searchFor("I like a apple");
    }

    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TC10013")
    @Link(name = "Google Link", url = "https://www.googgle.com")
    @DisplayName("TC10013: google search")
    @Description("Verify Google Logo")
    @Test
    public void verifyGoogleLogo() {
        googleHomePage.openApplication();
        googleHomePage.verifyGoogleLogo();
    }

}
