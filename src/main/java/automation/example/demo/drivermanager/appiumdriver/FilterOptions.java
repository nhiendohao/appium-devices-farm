package automation.example.demo.drivermanager.appiumdriver;


import org.apache.http.util.CharArrayBuffer;

import java.util.Collection;

public class FilterOptions {
    private String name;
    private String udid;
    private String platform;
    private String platformVersion;
    private boolean busy;
    private boolean realDevice;

    public FilterOptions(String name, String udid, String platform, String platformVersion, boolean busy, boolean realDevice) {
        this.name = name;
        this.udid = udid;
        this.platform = platform;
        this.platformVersion = platformVersion;
        this.busy = busy;
        this.realDevice = realDevice;
    }


    public boolean isRealDevice() {
        return realDevice;
    }

    public void setRealDevice(boolean realDevice) {
        this.realDevice = realDevice;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }
}