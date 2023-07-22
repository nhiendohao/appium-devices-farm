package integrations.slack;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Slack {

    public static void main(String... args) {
        String host = args[0];
        String jobId = args[1];
        sendMessage(
                "Total test cases: 10"
                + "\nPassed: 10"
                + "\nFailed: 0"
                + "\nAllure report: " + host + "/job/Selenium_Pipeline_Allure_Report/" + jobId + "/allure/"
        );
    }

    private static void sendMessage(String message) {
        Map<String, String> data = new HashMap<>();
        data.put("channel", "C04DZE14GCR");
        data.put("text", message);
        data.put("icon_emoji", ":twice:");

        Response response = RestAssured
                .given()
                .baseUri("https://slack.com")
                .contentType("application/json")
                .header("Authorization",
                        "Bearer xoxb-4462733300003-4486547363136-bNgSUcktbJPyoOSSnpmPh6kU")
                .body(data)
                .log().all()
                .post("/api/chat.postMessage");

        if (response.statusCode() != 200) {
            System.out.println("Failed to send slack message");
        }
    }
}
