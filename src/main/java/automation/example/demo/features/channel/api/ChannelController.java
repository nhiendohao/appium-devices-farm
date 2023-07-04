package automation.example.demo.features.channel.api;

import static automation.example.demo.config.BaseConfig.getBaseConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Assertions;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import automation.example.demo.models.Channel;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ChannelController {
    private final String baseApiUrl = getBaseConfig().getEnvironment().getBaseApiUrl();

    @Step("Create Channel by API")
    public void createChannelByAPI(Channel channel) {
        RestAssured.given().filter(new AllureRestAssured())
                   .baseUri(this.baseApiUrl)
                   .contentType("application/json")
                   .body(new Gson().toJson(channel))
                   .log().all()
                   .when()
                   .post("/api/channel/");
    }

    @Step("Get Channel by API")
    public void getChannelByAPI(String channelId, String channelName) {
        RestAssured.given().filter(new AllureRestAssured())
                   .baseUri(this.baseApiUrl)
                   .pathParams("channelId", channelId, "channelName", channelName, "pageNum", 0, "pageSize", 10)
                   .log().all()
                   .when()
                   .get("/api/channel/search?channelId="
                        + "{channelId}&channelName={channelName}&pageNum={pageNum}&pageSize={pageSize}");
    }

    @Step("Delete Channel by API")
    public void deleteChannelByAPI(String channelId) {
        RestAssured.given().filter(new AllureRestAssured())
                   .baseUri(this.baseApiUrl)
                   .pathParam("channelId", channelId)
                   .log().all()
                   .when()
                   .delete("/api/channel/{channelId}");
    }

    @Step("Get Channel from Response")
    public Channel getChannel(Response response, String channelId) {
        JsonArray channelList = new Gson().fromJson(response.prettyPrint(), JsonObject.class)
                                          .getAsJsonObject("result")
                                          .getAsJsonArray("list");

        for (int i = 0; i < channelList.size(); i++) {
            final JsonObject jsonChannel = channelList.get(i).getAsJsonObject();

            if (jsonChannel.get("channelId")
                           .getAsString()
                           .equalsIgnoreCase(channelId)) {
                // Convert JsonObject to Object
                return new Gson().fromJson(jsonChannel.toString(), Channel.class);
            }
        }
        return null;
    }

    @Step("Verify Channel are created successfully by API")
    public void verifyChannelCreatedSuccessfullyByAPI(Response actualChannel, Channel expectedChannel) {
        final Channel response = getChannel(actualChannel, expectedChannel.getChannelId());

//        Channel response2 = actualChannel.jsonPath()
//                                         .getObject("result.list", Channel.class);

        assertThat(response.getChannelId(), equalTo(expectedChannel.getChannelId()));
        assertThat(response.getChannelName(), equalTo(expectedChannel.getChannelName()));
        assertThat(response.getChannelSecret(), equalTo(expectedChannel.getChannelSecret()));
        assertThat(response.getRegion(), equalTo(expectedChannel.getRegion()));
        assertThat(response.isActive(), equalTo(expectedChannel.isActive()));
        Assertions.assertEquals(response.getChannelId(), expectedChannel.getChannelId());
        Assertions.assertEquals(response.getChannelName(), expectedChannel.getChannelName());
        Assertions.assertEquals(response.getChannelSecret(), expectedChannel.getChannelSecret());
        Assertions.assertEquals(response.getRegion(), expectedChannel.getRegion());
    }
}
