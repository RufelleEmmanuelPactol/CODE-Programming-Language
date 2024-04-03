package com.code.virtualmachine;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HTTP {

    private final HttpClient client;

    public HTTP() {
        this.client = HttpClient.newHttpClient();
    }

    public String get(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String post(String url, Map<Object, Object> data) throws IOException, InterruptedException {
        StringBuilder formData = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (formData.length() != 0) formData.append("&");
            formData.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            formData.append("=");
            formData.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(formData.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}

    // Add PUT, DELETE, etc. here as needed

