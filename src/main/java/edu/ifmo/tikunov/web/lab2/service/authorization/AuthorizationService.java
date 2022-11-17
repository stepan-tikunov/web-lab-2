package edu.ifmo.tikunov.web.lab2.service.authorization;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class AuthorizationService extends RestApiClient {

	public String authorize(String username, String password) throws ApiException {
		JsonObject payload = Json.createObjectBuilder()
			.add("username", username)
			.add("password", password)
			.build();

		JsonObject response = callPostMethod("login", payload);

		return response.getString("token");
	}

	public String register(String username, String password) throws ApiException {
		JsonObject payload = Json.createObjectBuilder()
			.add("username", username)
			.add("password", password)
			.build();

		JsonObject response = callPostMethod("create", payload);

		return response.getString("token");
	}

	public String getUsernameFromToken(String token) throws ApiException {
		JsonObject payload = Json.createObjectBuilder()
			.add("token", token).build();

		JsonObject response = callPostMethod("auth", payload);

		return response.getString("username");
	}

	public AuthorizationService(String apiRoot) {
		super(apiRoot);
	}
}
