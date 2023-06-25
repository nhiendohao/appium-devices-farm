package automation.example.demo.features.search.ui.pages;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import automation.example.demo.pageobject.PageObject;
import helpers.AllureReportHelper;
import io.qameta.allure.Step;

public class GoogleHomePage extends PageObject {
    @FindBy(xpath = "//textarea[@name='q']")
    WebElement searchBar;

    @FindBy(xpath = "//img[@class='lnXdpd']")
    WebElement googleLogo;

    public String searchBarString(String argument) {
        return String.format("//textarea[@name='%s']", argument);
    }

    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Google page")
    public void openApplication() {
        navigateTo(System.getProperty("baseurl"));
        AllureReportHelper.attachScreenshot(driver);
    }

    @Step("Search for keyword {keyword}")
    public void searchFor(String keyword) {
        enter(searchBar, keyword);
        searchBar.sendKeys(Keys.ENTER);
    }

    @Step("Verify Google Logo")
    public void verifyGoogleLogo() {
        assertTrue(isElementPresent(googleLogo));
    }
}
