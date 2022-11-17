package edu.ifmo.tikunov.web.lab2.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import edu.ifmo.tikunov.web.lab2.service.area.AreaCheckService;
import edu.ifmo.tikunov.web.lab2.service.area.PointCheck;
import edu.ifmo.tikunov.web.lab2.service.area.PointChecksContextManager;

@WebServlet(value = "/check")
public class AreaCheckServlet extends HttpServlet {

    private AreaCheckService service = new AreaCheckService();

    protected PointCheck parsePayload(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            double x = Double.parseDouble(req.getParameter("x"));
            double y = Double.parseDouble(req.getParameter("y"));
            double r = Double.parseDouble(req.getParameter("r"));

            return new PointCheck(x, y, r);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
        String username = (String) req.getAttribute("username");

        PointCheck p = parsePayload(req, resp);
        LocalDateTime reqDate = (LocalDateTime) req.getAttribute("date");
        LocalDateTime now = LocalDateTime.now();
        long executionTime = ChronoUnit.MILLIS.between(reqDate, now);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy HH:mm");
		String formattedDate = reqDate.format(formatter);


        if (service.check(p)) {
            p.setHit(true);
        }
        p.setDate(formattedDate);
        p.setExecutionTime(executionTime);

		try (PointChecksContextManager manager = new PointChecksContextManager(req, username)) {
			manager.addCheck(p);
		}

        resp.sendRedirect(req.getContextPath());
    }
}
