/**
 * TestRail API binding for Java (API v2, available since TestRail 3.0)
 * Updated for TestRail 5.7
 * <p>
 * Learn more:
 * <p>
 * http://docs.gurock.com/testrail-api2/start
 * http://docs.gurock.com/testrail-api2/accessing
 * <p>
 * Copyright Gurock Software GmbH. See license.md for details.
 */

package integrations.testrail;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIClient {
    private String username;
    private String password;
    private String baseUrl;

    public APIClient(String baseUrl, String username, String password) {
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
    }

    public String getUser() {
        return this.username;
    }

    public void setUser(String user) {
        this.username = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String getAuthorization(String user, String password) {
        try {
            return new String(Base64.getEncoder().encode((user + ":" + password).getBytes()));
        } catch (IllegalArgumentException e) {
            // Not thrown
        }
        return "";
    }

    private String getToken() {
        return getAuthorization(this.username, this.password);
    }

    public Response attachImageToCase(String resultId) {
        String auth = getToken();

        Response response = RestAssured
                .given()
                .baseUri(this.baseUrl)
                .header("Authorization", "Basic " + auth)
                .header("Content-Type", "multipart/form-data")
                .when().log().all()
                .multiPart("file", new File("src/test/resources/media/image/example_brown.png"))
                .post(String.format("index.php?/api/v2/add_attachment_to_result/%s", resultId));

        if (response.statusCode() == 200) {
            System.out.println("Attach image to " + resultId + " on Testrail Testrail.");
        } else {
            System.out.println("Failed to Attach image to " + resultId + " on Testrail Testrail.");
        }
        return response;
    }

    public Response AddResultForCases(String runId, Map data) {
        String auth = getToken();

        Response response = RestAssured
                .given()
                .baseUri(this.baseUrl)
                .header("Authorization", "Basic " + auth)
                .header("Content-Type", "application/json")
                .when().log().all()
                .body(data)
                .post(String.format("index.php?/api/v2/add_results_for_cases/%s", runId));

        if (response.statusCode() == 200) {
            System.out.println("Upload test results for RunId " + runId + " to Testrail successfully.");
        } else {
            System.out.println("Failed to upload test results for RunId " + runId + " to Testrail.");
        }
        return response;
    }

    public Response AddResultForCase(String runId, String caseId, Map data) {
        String auth = getToken();

        Response response = RestAssured
                .given()
                .baseUri(this.baseUrl)
                .header("Authorization", "Basic " + auth)
                .header("Content-Type", "application/json")
                .when().log().all()
                .body(data)
                .post(this.baseUrl + String.format("index.php?/api/v2/add_result_for_case/%s/%s", runId, caseId));

        if (response.statusCode() == 200) {
            System.out.println("Upload test result " + caseId + " to Testrail successfully.");
        } else {
            System.out.println("Failed to upload test result " + caseId + " to Testrail.");
        }
        return response;
    }

    /**
     *
     * @param projectId
     * @param suiteId
     * @return
     */
    private List<Integer> getCaseIds(String projectId, String suiteId) {
        String auth = getToken();
        List<Integer> caseIds = new ArrayList<>();

        Response response = RestAssured
                .given()
                .baseUri(this.baseUrl)
                .header("Authorization", "Basic " + auth)
                .header("Content-Type", "application/json")
                .when().log().all()
                .get(String.format("index.php?/api/v2/get_cases/%s&suite_id=%s", projectId, suiteId));

        JsonArray jsonArray = new Gson().fromJson(response.prettyPrint(), JsonArray.class);
        for (int i = 0; i < jsonArray.size(); i++) {
            int caseId = jsonArray.get(i).getAsJsonObject().get("id").getAsInt();
            caseIds.add(caseId);
        }
        return caseIds;
    }

    /**
     *
     * @param projectId
     * @param suiteId
     * @param runName
     * @return
     */
    public String addTestRun(String projectId, String suiteId, String runName) {
        String auth = getAuthorization(this.username, this.password);
        List<Integer> caseIds = getCaseIds(projectId, suiteId);
        Map data = new HashMap();
        data.put("suite_id", suiteId);
        data.put("name", runName);
        data.put("case_ids", caseIds);

        Response response = RestAssured
                .given()
                .baseUri(this.baseUrl)
                .header("Authorization", "Basic " + auth)
                .header("Content-Type", "application/json")
                .when().log().all()
                .body(data)
                .post(String.format("index.php?/api/v2/add_run/%s", projectId));

        JsonObject jsonObject = new Gson().fromJson(response.prettyPrint(), JsonObject.class);
        return jsonObject.get("id").getAsString();
    }
}
