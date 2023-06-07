package automation.example.demo.features.search.ui.pages;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.example.demo.pageobject.PageObject;
import io.qameta.allure.Step;

public class GoogleHomePage extends PageObject {
    @FindBy(xpath = "//textarea[@name='q']")
    WebElement SEARCH_BAR;

    @FindBy(xpath = "//img[@class='lnXdpd']")
    WebElement GOOGLE_LOGO;

    public String SEARCH_BAR_STRING(String argument) {
        return String.format("//textarea[@name='%s']", argument);
    }

    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Google page")
    public void openApplication() {
        navigateTo("https://www.google.com.vn");
    }

    @Step("Search for keyword")
    public void searchFor(String keyword) {
        enter(SEARCH_BAR, keyword);
        SEARCH_BAR.sendKeys(Keys.ENTER);
    }

    @Step("Verify Google Logo")
    public void verifyGoogleLogo() {
        assertTrue(isElementPresent(GOOGLE_LOGO));
    }
}
