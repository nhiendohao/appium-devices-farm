package helpers;

import java.io.ByteArrayInputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Allure;

public class AllureReportHelpers {

    public static void attachScreenshot(WebDriver driver) {
        Allure.addAttachment(
                "screenshot", new ByteArrayInputStream(
                        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

}
