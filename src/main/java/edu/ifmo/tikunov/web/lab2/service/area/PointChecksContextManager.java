package edu.ifmo.tikunov.web.lab2.service.area;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

public class PointChecksContextManager implements Closeable {

	private String username;
	private ServletContext context;
	private HttpServletRequest req;
	private List<PointCheck> checks;

	@SuppressWarnings("unchecked")
	public PointChecksContextManager(HttpServletRequest req, String username) {
		this.username = username;
		this.context = req.getServletContext();
		this.req = req;
		checks = (List<PointCheck>) context.getAttribute(username);
		if (checks == null) {
			checks = new ArrayList<>();
		}
	}

	public List<PointCheck> getChecks() {
		return checks;
	}

	public void addCheck(PointCheck check) {
		checks.add(check);
	}

	@Override
	public void close() throws IOException {
		context.setAttribute(username, checks);
		req.setAttribute("checks", checks);
	}
}
