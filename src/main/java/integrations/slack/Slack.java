package integrations.slack;

import static helpers.FileHelpers.readJsonFileIntoObject;

import java.util.HashMap;
import java.util.Map;

import integrations.slack.jsonreport.JsonReport;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Slack {

    public static void main(String[] args) {
        JsonReport jsonReport = readJsonFileIntoObject(JsonReport.class, "target/jsonReport.json");
        String host = args[0];
        String jobName = args[1];
        String jobId = args[2];
        sendMessage(
                "Total test cases: " + jsonReport.getTotalTestCases()
                + "\nPassed: " + jsonReport.getTotalPasses()
                + "\nFailed: " + + jsonReport.getTotalFails()
                + "\nAllure report: " + host + "job/" + jobName + "/" + jobId + "/allure/"
        );
    }

    private static void sendMessage(String message) {
        Map<String, String> data = new HashMap<>();
        data.put("channel", "");
        data.put("text", message);
        data.put("icon_emoji", ":twice:");

        Response response = RestAssured
                .given()
                .baseUri("https://slack.com")
                .contentType("application/json")
                .header("Authorization",
                        "Bearer sampleBearer")
                .body(data)
                .log().all()
                .post("/api/chat.postMessage");

        if (response.statusCode() != 200) {
            System.out.println("Failed to send slack message");
        }
    }
}
