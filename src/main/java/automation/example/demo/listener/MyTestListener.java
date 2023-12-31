package automation.example.demo.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestExecutionResult.Status;
import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import helpers.FileHelpers;
import integrations.slack.jsonreport.JsonReport;
import integrations.testrail.BaseTestrail;
import integrations.testrail.TestStatus;
import integrations.testrail.TestrailConfig;

public class MyTestListener implements TestExecutionListener {
    private static int totalPasses = 0;
    private static int totalFails = 0;
    private static int totalSkips = 0;

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        TestExecutionListener.super.testPlanExecutionStarted(testPlan);
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        TestExecutionListener.super.testPlanExecutionFinished(testPlan);
        int totalTestCases = totalPasses + totalFails + totalSkips;
        JsonReport jsonReport = JsonReport.builder()
                                          .totalTestCases(totalTestCases)
                                          .totalPasses(totalPasses)
                                          .totalFails(totalFails)
                                          .totalSkips(totalSkips)
                                          .build();
        FileHelpers.writeObjectAsJsonFile(jsonReport, "target/jsonReport.json");
    }

    @Override
    public void dynamicTestRegistered(TestIdentifier testIdentifier) {
        TestExecutionListener.super.dynamicTestRegistered(testIdentifier);
    }

    @Override
    public void executionSkipped(TestIdentifier testIdentifier, String reason) {
        TestExecutionListener.super.executionSkipped(testIdentifier, reason);
    }

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        TestExecutionListener.super.executionStarted(testIdentifier);
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        TestExecutionListener.super.executionFinished(testIdentifier, testExecutionResult);

        if (testIdentifier.isTest()) {
            /**
             * Add results to JsonReport
             */
            switch (testExecutionResult.getStatus()) {
                case SUCCESSFUL:
                    totalPasses++;
                    break;
                case FAILED:
                    totalFails++;
                default:
                    totalSkips++;
                    break;
            }

            /**
             * Update test results if isRun=true
             */
            if (TestrailConfig.isRun) {
                String runId = TestrailConfig.runId;

                // Get caseId use
                String testCaseIdRegex = "\\bC(\\d+)\\b";
                String inputString = testIdentifier.getDisplayName();
                List<String> caseIds = new ArrayList<>();
                Pattern pattern = Pattern.compile(testCaseIdRegex);
                Matcher matcher = pattern.matcher(inputString);

                while (matcher.find()) {
                    String caseId = matcher.group();
                    caseIds.add(caseId);
                }

                String testcaseId = caseIds.get(0).substring(1);

                // Create test run if runId is blank
                BaseTestrail baseTestrail = new BaseTestrail();
                if (runId.equals("")) {
                    runId = baseTestrail.addTestRun(TestrailConfig.projectId, TestrailConfig.suiteId,
                                                    "Selenium Regression Test Demo");
                }

                // Update test results to Testrail
                TestStatus testStatus = testExecutionResult.getStatus().equals(Status.SUCCESSFUL)
                                        ? TestStatus.SUCCESS : TestStatus.FAILURE;
                Map<String, String> testResult = baseTestrail.getTestResult(testStatus);
                baseTestrail.addResultForCase(runId, testcaseId, testResult);
            }
        }
    }

    @Override
    public void reportingEntryPublished(TestIdentifier testIdentifier, ReportEntry entry) {
        TestExecutionListener.super.reportingEntryPublished(testIdentifier, entry);
    }
}
