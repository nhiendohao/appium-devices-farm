## API spec: API to upload test result to Testrail
Link: 
- https://support.testrail.com/hc/en-us/articles/15758390538260-Importing-test-results#creating-a-test-run-0-0
- https://support.testrail.com/hc/en-us/articles/15758390538260-Importing-test-results#importing-attachments-0-3
- https://support.testrail.com/hc/en-us/articles/7077292642580-Cases
- https://support.testrail.com/hc/en-us/articles/7077039051284-Accessing-the-TestRail-API

### Add result for case
URL: index.php?/api/v2/add_result_for_case/{run_id}/{case_id}
Headers:
- Content-Type: application/json
Method: POST
Body:

**CURL command**
curl -X POST \
-u "user@example.com:password" \
-F "@path/to/data.json" \
"https://example.testrail.io/index.php?/api/v2/add_results_for_cases/228"

```
{
    "status_id": 5,
    "comment": "This test failed",
    "elapsed": "15s",
    "defects": "TR-7",
    "version": "1.0 RC1 build 3724",
    "custom_step_results": [
        {
            "content": "Step 1",
            "expected": "Expected Result 1",
            "actual": "Actual Result 1",
            "status_id": 1
        },
        {
            "content": "Step 2",
            "expected": "Expected Result 2",
            "actual": "Actual Result 2",
            "status_id": 2
        }
    ]
}
```

### Add result for cases
URL: index.php?/api/v2/add_results_for_cases/{run_id}
Headers:
- Content-Type: application/json
Method: POST

Body:
```
{
    "results": [
        {
            "case_id": 1,
            "status_id": 5,
            "comment": "This test failed",
            "defects": "TR-7"
        },
        {
            "case_id": 2,
            "status_id": 1,
            "comment": "This test passed",
            "elapsed": "5m",
            "version": "1.0 RC1"
        },
        {
            "case_id": 1,
            "assignedto_id": 5,
            "comment": "Assigned this test to Joe"
        }
    ]
}
```

### Attach image for case
URL: /index.php?/api/v2/add_attachment_to_result/{case_id}
Headers:
- Content-Type: multipart/form-data
Method: POST

**CURL command**
curl -X POST \
-u "user@example.com:password" \
-H "Content-Type: multipart/form-data" \
-F "attachment=@C:\\image.jpg" \
"https://example.testrail.io/index.php?/api/v2/add_attachment_to_result/8661"

TestRail API Binding for Java
-----------------------------

You can learn more about TestRail's API and how to use the Java binding here:
http://docs.gurock.com/testrail-api2/start
http://docs.gurock.com/testrail-api2/bindings-java

binding uses the JSON.simple library for encoding/decoding JSON data, copyright Yidong Fang, licensed under the Apache 2.0 license and available here:
https://code.google.com/p/json-simple/

For questions, suggestions, or other requests, please reach out to us through our support channels:
https://www.gurock.com/testrail/support
