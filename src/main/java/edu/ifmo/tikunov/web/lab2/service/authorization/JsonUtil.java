package edu.ifmo.tikunov.web.lab2.service.authorization;

import java.io.InputStream;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class JsonUtil {
	public static JsonObject parseObject(InputStream in) {
		JsonReader reader = Json.createReader(in);

		return reader.readObject();
	}
}
