package automation.example.demo.models;

import static automation.example.demo.constants.Constants.CHANNEL_LIST_FILE_PATH;

import java.util.Map;

import helpers.DataLoaderHelpers;
import lombok.Data;

@Data
public class ChannelList {
    private static ChannelList instance;
    private Map<String, Channel> channelList;

    public static synchronized ChannelList getInstance() {
        if (instance == null) {
            instance = DataLoaderHelpers.loadDataFromSource(CHANNEL_LIST_FILE_PATH, ChannelList.class);
        }
        return instance;
    }
}
