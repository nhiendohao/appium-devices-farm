package automation.example.demo.drivermanager.appiumdriver;


import java.io.IOException;
import java.net.ServerSocket;
import java.time.Duration;

import helpers.ApiHelper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
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

import static automation.example.demo.constants.Constants.APPIUM_SERVER_STARTUP_TIME;
import static automation.example.demo.drivermanager.appiumdriver.Devices.*;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.*;

public class AppiumManager {
    private static final Logger logger = LoggerFactory.getLogger(AppiumManager.class);
    private static AppiumDriverLocalService appiumLocal;

    private static int port = 4723;
    private static String remoteAppium = System.getProperty("remote_appium");
    private static String remoteAppiumPort = System.getProperty("remote_appium_port");


    public static void startAppiumServer() {
        Duration startUpTimeout = Duration.ofMillis(APPIUM_SERVER_STARTUP_TIME);
        AppiumServiceBuilder builder = new AppiumServiceBuilder().withArgument(GeneralServerFlag.RELAXED_SECURITY).withArgument(SESSION_OVERRIDE).withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/");

        if (!remoteAppium.isEmpty()) {
            if (isAppiumAvailable("http://" + remoteAppium + ":" + remoteAppiumPort + "/wd/hub/sessions")) {
                logger.info("Using existing......appium" + remoteAppium);
                builder.withIPAddress(remoteAppium).usingPort(Integer.parseInt(remoteAppiumPort));
                appiumLocal = builder.build();
            } else logger.error("Remote Appium server is not running on remote URL");
        } else {
            logger.info(logger.getName() + "Starting Appium Server on the localhost");
            builder.usingAnyFreePort().withTimeout(startUpTimeout);
            appiumLocal = builder.build();
            appiumLocal.start();
        }
    }

    public static String getRemoteWDHubIp() {
        if (remoteAppium.isEmpty()) {
            return "127.0.0.1";
        }
        return remoteAppium;
    }

    public static String getRemoteWDHubPort() {
        if (remoteAppiumPort.isEmpty()) {
            return "4723";
        }
        return remoteAppiumPort;
    }

    public static boolean isServerRunningAtPort(int port) {
        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            // If control comes here, then it means that the port is in use
            isServerRunning = true;
        } finally {
            serverSocket = null;
        }
        return isServerRunning;
    }

    public static void stopAppiumServer() {
        appiumLocal.stop();
        if (appiumLocal.isRunning()) {
            logger.info("Appium server didn't shutdown. Trying to quit again....");
            appiumLocal.stop();
        }
    }

    public static void deleteAppiumSession(String sessionId) {
        try {
            String appiumURL = String.format("http://%s:%s/wd/hub/session/%s", remoteAppium, remoteAppiumPort, sessionId);
            logger.info("Calling Delete session for session ID: {}", sessionId);
            JSONObject response = new ApiHelper().sendDeleteRequestJSON(appiumURL);
            logger.info("Delete session {} response: {}", sessionId, response.toString());
        } catch (Exception e) {
            logger.error("Error while deleting Appium session {}: {}", sessionId, e.getMessage());
        }
    }

    public static void waitForSessionEnded(String sessionId, int timeOut) {

        try {
            Thread.sleep(3000);
            if (isAppiumAvailable("http://" + remoteAppium + ":" + remoteAppiumPort + "/wd/hub/sessions/")) {
                logger.info("Wait for Session " + sessionId + " is ended");
            } else {
                while (!isSessionEnded(sessionId) && timeOut > 0) {
                    Thread.sleep(4000);
                    timeOut = timeOut - 4000;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static AppiumDriver startAppiumDriver(DesiredCapabilities desiredCapabilities) {
        final AppiumDriver driverSession;

        if (desiredCapabilities.getPlatformName().is(Platform.ANDROID)) {
            driverSession = new AndroidDriver(appiumLocal.getUrl(), desiredCapabilities);
            logger.info("Session Created for Android ---- " + driverSession.getSessionId() + "---" + driverSession.getCapabilities().getCapability("udid"));
        } else if (desiredCapabilities.getPlatformName().is(Platform.IOS)) {
            logger.info("Appium URL ---- " + appiumLocal.getUrl() + "---" + desiredCapabilities.getPlatformName());
            driverSession = new IOSDriver(appiumLocal.getUrl(), desiredCapabilities);
            logger.info("Session Created for iOS ---- " + driverSession.getSessionId() + "---" + driverSession.getCapabilities().getCapability("udid"));
        } else {
            driverSession = new AppiumDriver(appiumLocal.getUrl(), desiredCapabilities);
            logger.info("Session Created ---- " + driverSession.getSessionId() + "---" + driverSession.getRemoteAddress().getHost() + "---" + driverSession.getCapabilities().getCapability("udid"));

        }
        return driverSession;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static Boolean isAppiumAvailable(String appiumUrl) {
        int maxAttempts = 10;
        int delaySeconds = 5;

        for (int currentAttempt = 1; currentAttempt <= maxAttempts; currentAttempt++) {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                // Send an HTTP GET request
                HttpGet httpGet = new HttpGet(appiumUrl);

                try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                    // Check the response code
                    int responseCode = httpResponse.getStatusLine().getStatusCode();
                    if (responseCode == 200) {
                        return true; // Appium is available
                    }
                }

            } catch (Exception e) {
                // Ignore any exceptions and continue to the next attempt
                logger.warn("Exception while checking Appium availability: {}", e.getMessage());
            }

            // Wait for a few seconds before the next attempt
            try {
                Thread.sleep(delaySeconds * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Thread interrupted while waiting for Appium availability.");
            }

            logger.info("Attempt {}: Appium not available yet.", currentAttempt);
        }
        logger.info("Remote Appium server at {} is not available", appiumUrl);
        return false; // Appium is not available after the maximum number of attempts
    }
}
