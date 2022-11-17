package edu.ifmo.tikunov.web.lab2.service.authorization;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import jakarta.json.JsonObject;

public class RestApiClient {
	private String apiRoot;

	private boolean isOk(HttpResponse response) {
		return response.getStatusLine().getStatusCode() < 400;
	}

	protected JsonObject callPostMethod(String method, JsonObject body) throws ApiException {
		String url = apiRoot + "/" + method;
		HttpClient http = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(body.toString(), "UTF-8");

		post.setEntity(entity);
		post.setHeader("Content-Type", "application/json");

		try {
			HttpResponse response = http.execute(post);
			JsonObject responseJson;
			try {
				InputStream in = response.getEntity().getContent();
				responseJson = JsonUtil.parseObject(in);
			} catch (Exception e) {
				throw new ApiException(ApiExceptionType.SERVER_BAD_RESPONSE);
			}
			if (isOk(response)) {
				return responseJson;
			} else {
				ApiExceptionType type;
				try {
					type = ApiExceptionType.valueOf(responseJson.getString("message"));
				} catch (Exception e) {
					type = ApiExceptionType.SERVER_BAD_RESPONSE;
				}
				throw new ApiException(type);
			}
		} catch (IOException e) {
			throw new ApiException(ApiExceptionType.SERVER_NOT_REACHED);
		}
	}

	public RestApiClient(String apiRoot) {
		this.apiRoot = apiRoot;
	}
}
