package automation.example.demo.features.login.ui.web.pages.google;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import automation.example.demo.pageobject.PageObject;
import io.qameta.allure.Step;

public class GooglePage extends PageObject {
    By SEARCH_BAR = By.xpath("//textarea[@name='q']");
    By GOOGLE_LOGO = By.xpath("//img[@class='lnXdpd']");

    public String SEARCH_BAR_STRING(String argument) {
        return String.format("//textarea[@name='%s']", argument);
    }

    public GooglePage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Google page")
    public void openApplication() {
        navigateTo("https://www.google.com.vn");
    }

    @Step("Search for keyword")
    public void searchFor(String keyword) {
        enter(SEARCH_BAR, keyword);
        enterKey(Keys.ENTER);
    }

    @Step("Verify Google Logo")
    public void verifyGoogleLogo() {
        assertTrue(isElementPresent(GOOGLE_LOGO));
    }
}
