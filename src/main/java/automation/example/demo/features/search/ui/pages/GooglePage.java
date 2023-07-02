package automation.example.demo.features.search.ui.pages;

import static automation.example.demo.config.BaseConfig.getBaseConfig;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import automation.example.demo.pageobject.PageObject;
import helpers.AllureReportHelpers;
import io.qameta.allure.Step;

public class GooglePage extends PageObject {
    By searchBar = By.xpath("//textarea[@name='q']");
    By googleLogo = By.xpath("//img[@class='lnXdpd']");

    public String searchBarString(String argument) {
        return String.format("//textarea[@name='%s']", argument);
    }

    public GooglePage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Google page")
    public void openApplication() {
        navigateTo(getBaseConfig().getEnvironment().getBaseUrl());
        AllureReportHelpers.attachScreenshot(driver);
    }

    @Step("Search for keyword {keyword}")
    public void searchFor(String keyword) {
        enter(searchBar, keyword);
        enterKey(Keys.ENTER);
    }

    @Step("Verify Google Logo")
    public void verifyGoogleLogo() {
        assertTrue(isElementPresent(googleLogo));
    }
}
