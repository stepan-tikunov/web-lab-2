package edu.ifmo.tikunov.web.lab2.service.authorization;

public class ApiException extends Exception {
	public final ApiExceptionType type;

	public ApiException(ApiExceptionType type) {
		this.type = type;
	}
}
