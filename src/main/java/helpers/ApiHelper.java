package helpers;


import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;



public class ApiHelper {
    public String sendGetRequest(String url) {
        String body;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            body = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send GET request to " + url + ": " + e.getMessage());
        }
        return body;
    }

    public JSONObject sendGetRequestJSON(String url) {
        JSONObject jsonObject;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            String body = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            jsonObject = new JSONObject(body);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send GET request to " + url + ": " + e.getMessage());
        }
        return jsonObject;
    }

    public JSONObject sendDeleteRequestJSON(String url) {
        JSONObject jsonObject;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpDelete request = new HttpDelete(url);
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            String body = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            jsonObject = new JSONObject(body);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send DELETE request to " + url + ": " + e.getMessage());
        }
        return jsonObject;
    }
}