package com.benchew.swapisearch.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncHttpClient {
	static final private HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(5))
            .build();

	public String sendRequest(String url) {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();				
		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
		String body = null;
		Integer statusCode = null; 
		
		try {
			body = response.thenApply(HttpResponse::body).get(10, TimeUnit.SECONDS);
			 statusCode = response.thenApply(HttpResponse::statusCode).get(10, TimeUnit.SECONDS); 
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}

		if (statusCode != 200) {
            throw new RuntimeException("HTTP Status Code: " + statusCode + " for URL: " + request.uri());
        }
		
		return body;
	}
}
