package edu.ifmo.tikunov.web.lab2.servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import edu.ifmo.tikunov.web.lab2.service.ApiException;
import edu.ifmo.tikunov.web.lab2.service.AuthorizationService;
import edu.ifmo.tikunov.web.lab2.service.PointCheck;
import edu.ifmo.tikunov.web.lab2.service.UserNotFoundException;

@WebFilter(value = "/*")
public class AuthorizationFilter implements Filter {

	private AuthorizationService service;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String host = filterConfig.getInitParameter("host");
		service = new AuthorizationService(host);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String token = (String) req.getSession().getAttribute("token");

		if (token == null) {
			req.setAttribute("authorized", false);
			chain.doFilter(request, response);
			return;
		}

		req.setAttribute("authorized", true);

		try {
			String username = service.getUsernameFromToken(token);

			req.setAttribute("username", username);
			Object checks = req.getServletContext().getAttribute(username);
			if (checks == null) {
				checks = new ArrayList<PointCheck>();
				req.getServletContext().setAttribute(username, checks);
			}
			req.setAttribute("checks", checks);
		} catch (ApiException e) {
			resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} catch (UserNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {}

}
