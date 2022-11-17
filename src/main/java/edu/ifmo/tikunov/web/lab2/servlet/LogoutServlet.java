package edu.ifmo.tikunov.web.lab2.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogoutServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie tokenCookie = new Cookie("AUTHORIZATION_JWT_TOKEN", "");
		tokenCookie.setMaxAge(0);
		resp.addCookie(tokenCookie);
		resp.sendRedirect(req.getContextPath() + getInitParameter("loginUrl"));
	}
}
