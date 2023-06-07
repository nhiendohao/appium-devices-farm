package automation.example.demo.models;

import lombok.Data;

@Data
public class Channel {
    private String channelId;
    private String channelName;
    private String channelSecret;
    private String region;
    private boolean active;
}
