package automation.example.demo.features.youtube.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automation.example.demo.drivermanager.DriverManager;
import automation.example.demo.pageobject.MobileObject;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Step;

public class YoutubeHomePage extends MobileObject {
    @AndroidFindBy(accessibility = "Search")
    @iOSXCUITFindBy(accessibility = "Search")
    WebElement SEARCH_ICON;

    @AndroidFindBy(id = "com.google.android.youtube:id/search_edit_text")
    @iOSXCUITFindBy(id = "Search")
    WebElement SEARCH_BAR;

    public By suggestionList(int suggestionIndex) {
        Platform platform = DriverManager.getMobilePlatform(driver);
        if (Platform.ANDROID.equals(platform)) {
            return AppiumBy.xpath(String.format("(//android.widget.TextView)[%s]", suggestionIndex));
        }
        return AppiumBy.xpath(String.format("ios[%s]", suggestionIndex));
    }

    public YoutubeHomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Search for keyword")
    public void searchFor(String keyword) {
        clickOn(SEARCH_ICON);
        enter(SEARCH_BAR, keyword);
        waitUntilElementVisible(suggestionList(1), 30);
        clickOn(suggestionList(1));
    }

}