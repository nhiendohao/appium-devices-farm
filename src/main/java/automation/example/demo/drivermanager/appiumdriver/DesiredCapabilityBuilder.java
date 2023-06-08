package automation.example.demo.drivermanager.appiumdriver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;

import automation.example.demo.drivermanager.appiumdriver.models.DeviceCapabilities;
import automation.example.demo.drivermanager.appiumdriver.models.DeviceCapabilitiesList;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public class DesiredCapabilityBuilder {

    public DesiredCapabilities buildDesiredCapabilities(String udid) {
        final DeviceCapabilities deviceCapabilities = DeviceCapabilitiesList.getInstance()
                                                                            .getDeviceCapabilitiesList()
                                                                            .get(udid);

        if (MobilePlatform.ANDROID.equals(deviceCapabilities.getPlatformName())) {
            return androidDesiredCapabilities(deviceCapabilities);
        } else {
            return iosDesiredCapabilities(deviceCapabilities);
        }
    }

    private DesiredCapabilities commonDesiredCapabilities(DeviceCapabilities deviceCapabilities) {
        final DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                                   deviceCapabilities.getAutomationName());
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, deviceCapabilities.getPlatformName());
        capabilities.setCapability(MobileCapabilityType.UDID, deviceCapabilities.getUdid());
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceCapabilities.getDeviceName());
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                                   deviceCapabilities.getPlatformVersion());
//        capabilities.setCapability(MobileCapabilityType.APP, deviceCapabilities.getApp());
        capabilities.setCapability(MobileCapabilityType.NO_RESET, deviceCapabilities.isNoReset());
        // Force relaunch app if it is opening
        capabilities.setCapability("appium:forceAppLaunch", true);
        return capabilities;
    }

    private DesiredCapabilities iosDesiredCapabilities(DeviceCapabilities deviceCapabilities) {
        final DesiredCapabilities capabilities = commonDesiredCapabilities(deviceCapabilities);

        capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, deviceCapabilities.getBundleId());
        capabilities.setCapability(IOSMobileCapabilityType.XCODE_ORG_ID, deviceCapabilities.getXcodeOrgId());
        capabilities.setCapability(IOSMobileCapabilityType.XCODE_SIGNING_ID,
                                   deviceCapabilities.getXcodeSigningId());
        capabilities.setCapability(IOSMobileCapabilityType.USE_PREBUILT_WDA,
                                   deviceCapabilities.isUsePrebuiltWDA());
        capabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, getAvailablePort());
        //add appium:includeDeviceCapsToSessionInfo to void get stuck issue after starting AWD on Safari
        capabilities.setCapability("appium:includeDeviceCapsToSessionInfo", false);
        return capabilities;
    }

    private DesiredCapabilities androidDesiredCapabilities(DeviceCapabilities deviceCapabilities) {
        final DesiredCapabilities capabilities = commonDesiredCapabilities(deviceCapabilities);

        capabilities.setCapability(AndroidMobileCapabilityType.NATIVE_WEB_SCREENSHOT,
                                   deviceCapabilities.isNativeWebScreenshot());
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, deviceCapabilities.getAppPackage());
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                                   deviceCapabilities.getAppActivity());
        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, getAvailablePort());
//            capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, "D:/webdrivers/2
//            .42/chromedriver.exe");
        return capabilities;
    }

    private int getAvailablePort() {
        try {
            ServerSocket socket;
            socket = new ServerSocket(0);
            socket.setReuseAddress(true);
            int port = socket.getLocalPort();
            socket.close();
            return port;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
