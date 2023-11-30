package automation.example.demo.drivermanager.appiumdriver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.ApiHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static automation.example.demo.drivermanager.appiumdriver.AppiumManager.isAppiumAvailable;
import static helpers.FigletHelper.figlet;

public class Devices {
    private static final Logger logger = LoggerFactory.getLogger(Devices.class);
    private static volatile List<Device> deviceList;
    private static String remoteWDHubIP = AppiumManager.getRemoteWDHubIp();
    private static String remoteWDHubPort = AppiumManager.getRemoteWDHubPort();


    public static synchronized List<Device> getConnectedDevices() {
        try {
            if (deviceList == null) {
                String response = new ApiHelper().sendGetRequest(String.format("http://%s:%s/device-farm/api/devices", remoteWDHubIP, remoteWDHubPort));
                deviceList = Arrays.asList(new ObjectMapper().readValue(response, Device[].class));
            }

            if (Objects.requireNonNull(deviceList).isEmpty()) {
                figlet("No Devices Connected");
                // You may choose to throw an exception or log a warning instead of exiting the system
                throw new RuntimeException("No devices connected");
            }

        } catch (JsonProcessingException e) {
            logger.error("Error while processing JSON response: {}", e.getMessage());
            // Handle the exception or log an error message
        }

        return Collections.unmodifiableList(deviceList);
    }

    public static int countConnectedDevicesByPlatform(String platform) {
        return (int) getConnectedDevices().stream()
                .filter(device -> device.getPlatform().equalsIgnoreCase(platform))
                .count();
    }

    public static void waitForAvailableDevice(String platform, int timeOut) {
        int currentSession = getExistingSessionByPlatform(platform);
        int totalConnectedDevices = countConnectedDevicesByPlatform(platform);

        try {
            if (currentSession < totalConnectedDevices) {
                logger.info("Your device is available");
            } else {
                while (currentSession == totalConnectedDevices && timeOut > 0) {
                    Thread.sleep(4000);
                    timeOut = timeOut - 4000;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Filters the list of connected devices based on the provided filter options and device availability.
     *
     * @param filterOptions The filter options specifying the desired device characteristics.
     * @return A list of devices that match the filter options and are available.
     */
    public static List<Device> filterActiveDevicesByOption(FilterOptions filterOptions) {
        return getConnectedDevices().stream()
                .filter(device -> matchesFilterOptions(device, filterOptions) && isDeviceAvailable(device.getUdid()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves information about existing sessions from the Appium server.
     *
     * @return A JSONArray containing information about existing sessions.
     * @throws RuntimeException If the Appium server is not available.
     */
    public static synchronized JSONArray getExistingSession() {
        String sessionsUrl = String.format("http://%s:%s/wd/hub/sessions", remoteWDHubIP, remoteWDHubPort);

        try {
            if (!isAppiumAvailable(sessionsUrl)) {
                figlet("Appium Server is not available");
                throw new RuntimeException("Appium Server is not available");
            }

            JSONObject response = new ApiHelper().sendGetRequestJSON(sessionsUrl);
            return response.getJSONArray("value");

        } catch (Exception e) {
            logger.error("Error while retrieving existing sessions: {}", e.getMessage());
        }
        return new JSONArray();
    }

    /**
     * Gets the number of existing sessions based on the specified platform.
     *
     * @param platform The platform name to count existing sessions for.
     * @return The count of existing sessions with the specified platform.
     */
    public static synchronized int getExistingSessionByPlatform(String platform) {
        JSONArray existingSessions = getExistingSession();

        return (int) IntStream.range(0, existingSessions.length())
                .mapToObj(existingSessions::getJSONObject)
                .map(session -> session.getJSONObject("capabilities").getString("platformName"))
                .filter(existingSessionPlatform -> existingSessionPlatform.equalsIgnoreCase(platform))
                .count();
    }

    /**
     * Checks if a device with the specified UDID is available by comparing with existing sessions.
     *
     * @param udid The unique identifier (UDID) of the device.
     * @return {@code true} if the device is available; {@code false} otherwise.
     */
    public static synchronized Boolean isDeviceAvailable(String udid) {
        JSONArray existingSessions = getExistingSession();

        if (existingSessions.length() == 0) {
            logger.info("Device {} is available.", udid);
            return true;
        }

        for (Object session : existingSessions) {
            String existingSessionUDID = ((JSONObject) session).getJSONObject("capabilities").getString("udid");
            if (existingSessionUDID.contains(udid)) {
                logger.info("Device {} is not available.", udid);
                return false;
            }
        }

        logger.info("Device {} is available.", udid);
        return true;
    }

    /**
     * Checks if the specified Appium session has ended by querying the Appium server multiple times.
     *
     * @param sessionId The unique identifier of the Appium session.
     * @return {@code true} if the session has ended, {@code false} otherwise.
     * @throws RuntimeException If an exception occurs during the API call or response parsing.
     */
    public static Boolean isSessionEnded(String sessionId) {
        int maxRetries = 3;
        String sessionsEndpoint = "/wd/hub/sessions";

        for (int retryCount = 0; retryCount < maxRetries; retryCount++) {
            try {
                String appiumURL = String.format("http://%s:%s%s%s", remoteWDHubIP, remoteWDHubPort, sessionsEndpoint, sessionId);

                if (isAppiumAvailable(appiumURL)) {
                    JSONObject response = new ApiHelper().sendGetRequestJSON(appiumURL);
                    logger.info("Ending session: {}", response.toString());
                    return response.getJSONObject("result").getBoolean("is_completed");
                }
            } catch (Exception e) {
                logger.error("Error while checking session end status: {}", e.getMessage());
            }
        }

        return false;
    }

    private static boolean matchesFilterOptions(Device device, FilterOptions filterOptions) {
        if (filterOptions.isRealDevice() && !device.isRealDevice()) {
            return false;
        }
        // Perform the filtering based on the provided filter options
        if (filterOptions.getName() != null && !filterOptions.getName().isEmpty() && !device.getName().contains(filterOptions.getName())) {
            return false;
        }
        if (filterOptions.getUdid() != null && !filterOptions.getUdid().isEmpty() && !device.getUdid().equals(filterOptions.getUdid())) {
            return false;
        }
        if (filterOptions.getPlatform() != null && !filterOptions.getPlatform().isEmpty() && !device.getPlatform().equals(filterOptions.getPlatform())) {
            return false;
        }
        if (filterOptions.getPlatformVersion() != null && !filterOptions.getPlatformVersion().isEmpty() && !device.getSdk().equals(filterOptions.getPlatformVersion())) {
            return false;
        }
        return true;
    }
}
