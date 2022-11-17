package edu.ifmo.tikunov.web.lab2.servlet;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class ServletAwareHttpFilter implements Filter {
	protected Optional<String> getServletInitParameter(HttpServletRequest req, String name) {
		String servletName = req.getHttpServletMapping().getServletName();
		ServletRegistration registration = req.getServletContext().getServletRegistration(servletName);

		return Optional.ofNullable(registration.getInitParameter(name));
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		doHttpFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
	}

	abstract protected void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException;
}
