package edu.ifmo.tikunov.web.lab2.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value = "")
public class ControllerServlet extends HttpServlet {

    private static final String[] AREA_CHECK_PAYLOAD_PARAMETERS = { "x", "y", "r" };

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Stream.of(AREA_CHECK_PAYLOAD_PARAMETERS).allMatch(p -> req.getParameter(p) != null)) {
            LocalDateTime now = LocalDateTime.now();
            req.setAttribute("date", now);
            getServletContext().getRequestDispatcher("/check").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean authorized = (boolean) req.getAttribute("authorized");

        if (authorized) {
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

}
