package automation.example.demo.drivermanager.appiumdriver.mobile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceCapabilities {
    private String automationName;
    private String platformName;
    private String udid;
    private String deviceName;
    private String platformVersion;
    private String appPackage;
    private String appActivity;
    private String app;
    private boolean noReset;
    private String bundleId;
    private String xcodeOrgId;
    private String xcodeSigningId;
    private String wdaLocalPort;
    private String systemPort;
    private boolean usePrebuiltWDA;
    private boolean nativeWebScreenshot;
}
