package integrations.testrail;

public enum TestStatus {
    UNSUCCESSFUL(5, "Unsuccessful"),
    FAILURE(5, "Failed"),
    RETEST(4, "Retest"),
    UNTESTED(3, "Untested"),
    BLOCKED(2, "Blocked"),
    SUCCESS(1, "Passed");

    private int statusId;
    private String statusResult;

    TestStatus(int statusId, String statusResult) {
        this.statusId = statusId;
        this.statusResult = statusResult;
    }

    public String getStatusResult() {
        return statusResult;
    }

    public int getStatusId() {
        return statusId;
    }
}
