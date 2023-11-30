package helpers;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;


//
public class ApiHelper {
    private final HttpClient client;

    public ApiHelper() {
        this.client = HttpClientBuilder.create().build();
    }

    public String sendGetRequest(String url) {
        try {
            HttpResponse response = client.execute(new HttpGet(url));
            return getResponseBody(response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send GET request to " + url + ": " + e.getMessage(), e);
        }
    }

    public JSONObject sendGetRequestJSON(String url) {
        try {
            HttpResponse response = client.execute(new HttpGet(url));
            return new JSONObject(getResponseBody(response));
        } catch (Exception e) {
            throw new RuntimeException("Failed to send GET request to " + url + ": " + e.getMessage(), e);
        }
    }

    public JSONObject sendDeleteRequestJSON(String url) {
        try {
            HttpResponse response = client.execute(new HttpDelete(url));
            return new JSONObject(getResponseBody(response));
        } catch (Exception e) {
            throw new RuntimeException("Failed to send DELETE request to " + url + ": " + e.getMessage(), e);
        }
    }

    private String getResponseBody(HttpResponse response) throws Exception {
        try (InputStream inputStream = response.getEntity().getContent()) {
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
    }
}