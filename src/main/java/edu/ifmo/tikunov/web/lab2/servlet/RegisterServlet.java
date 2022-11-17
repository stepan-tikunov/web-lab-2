package edu.ifmo.tikunov.web.lab2.servlet;

import java.io.IOException;
import java.util.Optional;

import edu.ifmo.tikunov.web.lab2.service.authorization.ApiException;
import edu.ifmo.tikunov.web.lab2.service.authorization.AuthorizationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value = "/register")
public class RegisterServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		req.setAttribute("registerError", "none");
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		AuthorizationService service = (AuthorizationService) req.getAttribute("authorizationService");

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String again = req.getParameter("again");

		String continueUrl = Optional
			.ofNullable(getInitParameter("continueUrl"))
			.orElse("/");

		if (!password.equals(again)) {
			req.setAttribute("registerError", "again");
		} else {
			try {
				String token = service.register(username, password);
				Cookie tokenCookie = new Cookie("AUTHORIZATION_JWT_TOKEN", token);
				resp.addCookie(tokenCookie);
				resp.sendRedirect(req.getContextPath() + continueUrl);
			} catch (ApiException e) {
				switch (e.type) {
					case REGISTER_USERNAME_ERROR:
						req.setAttribute("registerError", "username");
						break;
					case REGISTER_PASSWORD_ERROR:
						req.setAttribute("registerError", "password");
						break;
					default:
						req.setAttribute("registerError", "unknown");
				}
			}
		}

		getServletContext().getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
	}
}
