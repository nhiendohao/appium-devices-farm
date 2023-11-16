package automation.example.demo.drivermanager.appiumdriver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.ApiHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static automation.example.demo.drivermanager.appiumdriver.AppiumManager.isAppiumAvailable;
import static helpers.FigletHelper.figlet;

public class Devices {
    private static final Logger logger = LoggerFactory.getLogger(Devices.class);
    private static volatile List<Device> deviceList;
    private static String remoteWDHubIP = AppiumManager.getRemoteWDHubIp();
    private static String remoteWDHubPort = AppiumManager.getRemoteWDHubPort();


    public static synchronized List<Device> getConnectedDevices() {
        if (deviceList == null) {
            String remoteWDHubIP = AppiumManager.getRemoteWDHubIp();
            String response = new ApiHelper().sendGetRequest("http://" + remoteWDHubIP + ":" +remoteWDHubPort + "/device-farm/api/devices");
            try {
                deviceList = Arrays.asList(new ObjectMapper().readValue(response, Device[].class));
            } catch (JsonProcessingException e) {

                e.printStackTrace();
            }
        }
        if (deviceList.size() == 0) {
            figlet("No Devices Connected");
            System.exit(0);
        }
        return deviceList;
    }

    public static int countConnectedDevicesByPlatform(String platform) {
        List<Device> connectedDevices = getConnectedDevices();
        int count = 0;
        if (connectedDevices.size() > 0) {
            for (Device device : connectedDevices) {
                if (device.platform.equalsIgnoreCase(platform))
                    count = count + 1;
            }
        }
        return count;
    }

    public static void waitForAvailableDevice(String platform, int timeOut) {
        int currentSession = getExistingSessionByPlatform(platform);
        int totalConnectedDevices = countConnectedDevicesByPlatform(platform);

        try {
            if (currentSession < totalConnectedDevices) {
                logger.info("Your device is available");
            } else {
                while (currentSession == totalConnectedDevices && timeOut > 0) {
                    logger.info("Waiting for available device in 4seconds");
                    Thread.sleep(4000);
                    timeOut = timeOut - 4000;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<Device> filterActiveDevicesByOption(FilterOptions filterOptions) {
        // Get list devices
        List<Device> activeDevices = getConnectedDevices();
        List<Device> filteredDevices = new ArrayList<>();
        for (Device device : activeDevices) {
            if (matchesFilterOptions(device, filterOptions) && isDeviceAvailable(device.udid)) {
                filteredDevices.add(device);
            }
        }
        //Return list devices match filterOption
        return filteredDevices;

    }

    public static synchronized JSONArray getExistingSession() {
        JSONArray existingSessions = new JSONArray();
        if (isAppiumAvailable("http://" + remoteWDHubIP + ":4723/wd/hub/sessions")) {
            JSONObject response = new ApiHelper().sendGetRequestJSON("http://" + remoteWDHubIP + ":4723/wd/hub/sessions");
            existingSessions = response.getJSONArray("value");
        } else {
            figlet("Appium Server is not available");
            System.exit(0);
        }
        return existingSessions;
    }

    public static synchronized int getExistingSessionByPlatform(String platform) {
        int existingSessionByPlatform = 0;
        JSONArray existingSessions = getExistingSession();
        if (existingSessions.length() != 0) {
            for (int i = 0; i < existingSessions.length(); i++) {
//                String existingSessionPlatform = existingSessions.getJSONObject(i).getJSONObject("capabilities").getJSONObject("desired").getString("platformName");
                String existingSessionPlatform = existingSessions.getJSONObject(i).getJSONObject("capabilities").getString("platformName");
                if (existingSessionPlatform.equalsIgnoreCase(platform)) {
                    existingSessionByPlatform = existingSessionByPlatform + 1;
                }
            }
        }
        return existingSessionByPlatform;
    }

    public static synchronized Boolean isDeviceAvailable(String udid) {
        boolean isAvailable = false;
        JSONArray existingSessions = getExistingSession();
        if (existingSessions.length() != 0) {
            isAvailable = true;
            for (int i = 0; i < existingSessions.length(); i++) {
                String existingSessionUDID = existingSessions.getJSONObject(i).getJSONObject("capabilities").getString("udid");
//                String existingSessionUDID = existingSessions.getJSONObject(i).getString("id");

                if (existingSessionUDID.contains(udid)) {
                    isAvailable = false;
                    break;
                }
            }
        } else
            isAvailable = true;

        System.out.println("Device " + udid + " availability is " + isAvailable);
        return isAvailable;
    }

    public static Boolean isSessionEnded(String sessionId) {
        boolean isCompleted = false;
        int maxRetries = 3;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try {
                String remoteWDHubIP = AppiumManager.getRemoteWDHubIp();
                String appiumURL = "http://" + remoteWDHubIP + ":4723/dashboard/api/sessions/" + sessionId;
                Boolean isReady = isAppiumAvailable("http://" + remoteWDHubIP + ":4723/wd/hub/sessions");
                if (isReady) {
                    isAppiumAvailable(appiumURL);
                    JSONObject response = new ApiHelper().sendGetRequestJSON(appiumURL);
                    System.out.println("Ending session ......... " + response.toString());
                    isCompleted = response.getJSONObject("result").getBoolean("is_completed");
                    break; // Exit the loop if the API call is successful
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return isCompleted;
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
