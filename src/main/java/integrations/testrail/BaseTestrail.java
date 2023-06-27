package integrations.testrail;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;

public class BaseTestrail {

    public String addTestRun(String projectId, String suiteId, String runName) {
        APIClient client = new APIClient(TestrailConfig.BASE_URL, TestrailConfig.EMAIL, TestrailConfig.API_KEY);
        return client.addTestRun(projectId, suiteId, runName);
    }

    public void addResultForCase(String runId, String testCaseId, Map data) {
        APIClient client = new APIClient(TestrailConfig.BASE_URL, TestrailConfig.EMAIL, TestrailConfig.API_KEY);
        Object statusId = data.get("status_id");

        if (statusId.equals("1")) {
            client.AddResultForCase(runId, testCaseId, data);
        } else if (statusId.equals("5")) {
            Response response = client.AddResultForCase(runId, testCaseId, data);
            String resultId = "";
            client.attachImageToCase(resultId);
        }
    }

    public Map getTestStatus(TestStatus testStatus) {
        String status_id = "";
        String status_message = "";

        switch (testStatus) {
            case SUCCESS:
                status_id = "1";
                status_message = testStatus.getStatusResult();
                break;
            case FAILURE:
            case UNSUCCESSFUL:
                status_id = "5";
                status_message = testStatus.getStatusResult();
                break;
        }

        Map data = new HashMap();
        data.put("status_id", status_id);
        data.put("comment", status_message);
        return data;
    }
}
