package edu.ifmo.tikunov.web.lab2.servlet;

import java.io.IOException;
import java.util.stream.Stream;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PostValidationFilter extends ServletAwareHttpFilter {

	@Override
	protected void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {

		if (!req.getMethod().equals("POST")) {
			chain.doFilter(req, resp);
		}

		String[] requiredParameters = getServletInitParameter(req, "requiredParameters")
			.map(ps -> ps.split(","))
			.orElse(new String[0]);

		boolean someAbsent = Stream.of(requiredParameters)
			.map(k -> req.getParameter(k))
			.anyMatch(p -> p == null);

		if (someAbsent) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			chain.doFilter(req, resp);
		}
	}
}
