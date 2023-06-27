package automation.example.demo.drivermanager.appiumdriver.models;

import java.util.Map;

import automation.example.demo.constants.Constants;
import helpers.DataLoaderHelper;
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
                instance = DataLoaderHelper.loadDataFromSource(
                        Constants.DEVICE_CAPABILITIES_FILE_PATH,
                        DeviceCapabilitiesList.class);
            }
        }
        return instance;
    }
}
