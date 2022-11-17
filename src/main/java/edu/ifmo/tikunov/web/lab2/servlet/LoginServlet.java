package edu.ifmo.tikunov.web.lab2.servlet;

import java.io.IOException;
import java.util.Optional;

import edu.ifmo.tikunov.web.lab2.service.authorization.ApiException;
import edu.ifmo.tikunov.web.lab2.service.authorization.AuthorizationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("loginError", "none");
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AuthorizationService service = (AuthorizationService) req.getAttribute("authorizationService");

		String username = req.getParameter("username");
		String password = req.getParameter("password");

		String continueUrl = Optional
			.ofNullable(getInitParameter("continueUrl"))
			.orElse("/");

		try {
			String token = service.authorize(username, password);
			Cookie tokenCookie = new Cookie("AUTHORIZATION_JWT_TOKEN", token);
			resp.addCookie(tokenCookie);
			resp.sendRedirect(req.getContextPath() + continueUrl);
		} catch (ApiException e) {
			switch(e.type) {
				case LOGIN_USERNAME_ERROR:
					req.setAttribute("loginError", "username");
					break;
				case LOGIN_PASSWORD_ERROR:
					req.setAttribute("loginError", "password");
					break;
				default:
					req.setAttribute("loginError", "unknown");
			}
		}

		getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
	}
}
