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
    WebElement searchIcon;

    @AndroidFindBy(id = "com.google.android.youtube:id/search_edit_text")
    @iOSXCUITFindBy(id = "Search")
    WebElement searchBar;

    public By suggestionList(int index) {
        Platform platform = DriverManager.getMobilePlatform(driver);
        if (platform.equals(Platform.ANDROID)) {
            return AppiumBy.xpath(String.format("(//android.widget.TextView)[%s]", index));
        } else {
            return AppiumBy.xpath(String.format("ios[%s]", index));
        }
    }

    public YoutubeHomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Search for keyword")
    public void searchFor(String keyword) {
        clickOn(searchIcon);
        enter(searchBar, keyword);
    }

    @Step("Choose an item from suggestion list")
    public void chooseAnItemFromSuggestionList(int index) {
        waitUntilElementVisible(suggestionList(index), 30);
        clickOn(suggestionList(index));
    }

}
