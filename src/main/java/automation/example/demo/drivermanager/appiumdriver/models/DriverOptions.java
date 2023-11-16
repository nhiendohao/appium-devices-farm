package automation.example.demo.drivermanager.appiumdriver.models;

public class DriverOptions {
    private String udid;
    private String platform;

    // Constructor
    public DriverOptions(String udid, String platform) {
        this.udid = udid;
        this.platform = platform;
    }

    // Getters and setters (optional)
    public String getUdid() {
        return udid;
    }

    public void setUdid(String platform) {
        this.udid = platform;
    }
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
