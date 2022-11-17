package edu.ifmo.tikunov.web.lab2.service.authorization;

public enum ApiExceptionType {
	SERVER_NOT_REACHED,
	SERVER_BAD_RESPONSE,
	LOGIN_USERNAME_ERROR,
	LOGIN_PASSWORD_ERROR,
	REGISTER_USERNAME_ERROR,
	REGISTER_PASSWORD_ERROR,
	TOKEN_CHECK_ERROR,
}
