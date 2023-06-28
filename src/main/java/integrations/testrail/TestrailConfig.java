package integrations.testrail;

public class TestrailConfig {

    public static final String projectId = System.getProperty("testrail.projectId");
    public static final String suiteId = System.getProperty("testrail.suiteId");
    public static final String runId = System.getProperty("testrail.runId");
    public static final boolean isRun = System.getProperty("testrail.run").equals("true");
    public static final String EMAIL = "username";
    public static final String API_KEY = "password/apikey";
    public static final String BASE_URL = "https://testrail.com/";
}
