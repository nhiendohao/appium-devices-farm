package automation.example.demo.drivermanager.appiumdriver.models;

import java.util.Map;

import automation.example.demo.constants.Constants;
import helpers.DataLoaderHelpers;
import lombok.Data;

@Data
public class DeviceCapabilitiesList {
    private static DeviceCapabilitiesList instance;
    private Map<String, DeviceCapabilities> deviceCapabilitiesList;

    private DeviceCapabilitiesList() {
    }

    public static DeviceCapabilitiesList getInstance() {
        if (instance == null) {
            synchronized (DeviceCapabilitiesList.class) {
                instance = DataLoaderHelpers.loadDataFromSource(
                        Constants.DEVICE_CAPABILITIES_FILE_PATH_NEW,
                        DeviceCapabilitiesList.class);
            }
        }
        return instance;
    }
}
