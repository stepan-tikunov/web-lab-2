package edu.ifmo.tikunov.web.lab2.service;

public class AuthorizationService {
	protected String host;

	public String authorize(String username, String password)
	throws ApiException, UserNotFoundException, PasswordException {
		return null;
	}

	public String register(String username, String password)
	throws ApiException, UsernameException, PasswordException {
		return null;
	}

	public String getUsernameFromToken(String token)
	throws ApiException, UserNotFoundException {
		return "stepa";
	}

	public AuthorizationService(String host) {
		this.host = host;
	}
}
