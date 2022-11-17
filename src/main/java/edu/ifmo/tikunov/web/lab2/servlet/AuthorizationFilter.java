package edu.ifmo.tikunov.web.lab2.servlet;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import edu.ifmo.tikunov.web.lab2.service.area.PointChecksContextManager;
import edu.ifmo.tikunov.web.lab2.service.authorization.ApiException;
import edu.ifmo.tikunov.web.lab2.service.authorization.AuthorizationService;

public class AuthorizationFilter extends ServletAwareHttpFilter {

	private AuthorizationService service;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String host = filterConfig
				.getInitParameter("authorizationServiceUrl");
		service = new AuthorizationService(host);
	}

	protected boolean redirectIfCan(HttpServletRequest req, HttpServletResponse resp, String urlParameterName) throws IOException {
		Optional<String> url = getServletInitParameter(req, urlParameterName);
		if (url.isPresent()) {
			resp.sendRedirect(req.getContextPath() + url.get());
		}

		return url.isPresent();
	}

	@Override
	public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		req.setAttribute("authorizationService", service);

		Cookie[] cookies = Optional
			.ofNullable(req.getCookies())
			.orElse(new Cookie[0]);

		Optional<String> token = Stream.of(cookies)
				.filter(cookie -> cookie.getName().equals("AUTHORIZATION_JWT_TOKEN"))
				.map(cookie -> cookie.getValue())
				.findAny();

		boolean authRequired = getServletInitParameter(req, "authRequired").isPresent();
		boolean hasToken = token.isPresent() && !token.get().equals("");

		if (!hasToken) {
			if (authRequired) {
				redirectIfCan(req, resp, "loginUrl");
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				chain.doFilter(req, resp);
			}
			return;
		} else if (!authRequired && redirectIfCan(req, resp, "continueUrl")) {
			return;
		}

		try {
			String username = service.getUsernameFromToken(token.get());
			req.setAttribute("username", username);
			try (PointChecksContextManager manager = new PointChecksContextManager(req, username)) {}
		} catch (ApiException e) {
			switch (e.type) {
				case SERVER_NOT_REACHED:
					resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
					return;
				case TOKEN_CHECK_ERROR:
					redirectIfCan(req, resp, "loginUrl");
					resp.sendError(HttpServletResponse.SC_FORBIDDEN);
					return;
				default:
					resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;
			}
		}

		chain.doFilter(req, resp);
	}
}
