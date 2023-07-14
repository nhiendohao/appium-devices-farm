package automation.example.demo.drivermanager.appiumdriver;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.USE_PLUGINS;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumManager {
    private static final Logger logger = LoggerFactory.getLogger(AppiumManager.class);
    private static AppiumDriverLocalService appiumLocal;
    private static int port = -1;

    public static void startAppiumServer() {
        logger.info(logger.getName() + "Starting Appium Server on the localhost");
        final AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withArgument(GeneralServerFlag.RELAXED_SECURITY)
                .withArgument(USE_PLUGINS, "images");
//                .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/");

        if (port != -1) {
            builder.usingPort(port);
        } else {
            builder.usingAnyFreePort();
        }

        appiumLocal = builder.build();
        appiumLocal.start();
        logger.info(logger.getName() + "Appium Server Started at......" + appiumLocal.getUrl());
    }

    public static void stopAppiumServer() {
        appiumLocal.stop();
        if (appiumLocal.isRunning()) {
            logger.info("Appium server didn't shutdown. Trying to quit again....");
            appiumLocal.stop();
        }
    }

    public static AppiumDriver startAppiumDriver(DesiredCapabilities desiredCapabilities) {
        final AppiumDriver driverSession;

        if (desiredCapabilities.getPlatformName().is(Platform.ANDROID)) {
            driverSession = new AndroidDriver(appiumLocal.getUrl(), desiredCapabilities);
            logger.info("Session Created for Android ---- "
                        + driverSession.getSessionId() + "---"
                        + driverSession.getCapabilities().getCapability("udid"));
        } else if (desiredCapabilities.getPlatformName().is(Platform.IOS)) {
            driverSession = new IOSDriver(appiumLocal.getUrl(), desiredCapabilities);
            logger.info("Session Created for iOS ---- "
                        + driverSession.getSessionId() + "---"
                        + driverSession.getCapabilities().getCapability("udid"));
        } else {
            driverSession = new AppiumDriver(appiumLocal.getUrl(), desiredCapabilities);
            logger.info("Session Created ---- "
                        + driverSession.getSessionId() + "---"
                        + driverSession.getRemoteAddress().getHost() + "---"
                        + driverSession.getCapabilities().getCapability("udid"));

        }
        return driverSession;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
