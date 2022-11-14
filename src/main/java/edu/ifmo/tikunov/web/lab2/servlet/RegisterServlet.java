package edu.ifmo.tikunov.web.lab2.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value = "/register")
public class RegisterServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean authorized = (boolean) req.getAttribute("authorized");

		if (authorized) {
			resp.sendRedirect(req.getContextPath());
		} else {
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
		}
	}
}
