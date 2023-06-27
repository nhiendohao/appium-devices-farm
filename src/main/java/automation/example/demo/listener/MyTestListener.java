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

import integrations.testrail.BaseTestrail;
import integrations.testrail.TestStatus;
import integrations.testrail.TestrailConfig;

public class MyTestListener implements TestExecutionListener {
    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        TestExecutionListener.super.testPlanExecutionStarted(testPlan);
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        TestExecutionListener.super.testPlanExecutionFinished(testPlan);
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

        if (TestrailConfig.isRun) {
            if (testIdentifier.isTest()) {
                String runId = TestrailConfig.runId;
                String testCaseIdRegex = "\\bC(\\d+)\\b";
                String inputString = testIdentifier.getDisplayName();
                List<String> caseIds = new ArrayList<>();

                Pattern pattern = Pattern.compile(testCaseIdRegex);
                Matcher matcher = pattern.matcher(inputString);
                while (matcher.find()) {
                    String caseId = matcher.group();
                    caseIds.add(caseId);
                }

                BaseTestrail baseTestrail = new BaseTestrail();
                if (runId.equals("")) {
                    runId = baseTestrail.addTestRun(TestrailConfig.projectId, TestrailConfig.suiteId,
                                                    "Selenium Regression Test Demo");
                }

                String testcaseId = caseIds.get(0).substring(1);
                if (testExecutionResult.getStatus().equals(Status.SUCCESSFUL)) {
                    Map data = baseTestrail.getTestStatus(TestStatus.SUCCESS);
                    baseTestrail.addResultForCase(runId, testcaseId, data);
                } else {
                    Map data = baseTestrail.getTestStatus(TestStatus.FAILURE);
                    baseTestrail.addResultForCase(runId, testcaseId, data);
                }
            }
        }

    }

    @Override
    public void reportingEntryPublished(TestIdentifier testIdentifier, ReportEntry entry) {
        TestExecutionListener.super.reportingEntryPublished(testIdentifier, entry);
    }
}
