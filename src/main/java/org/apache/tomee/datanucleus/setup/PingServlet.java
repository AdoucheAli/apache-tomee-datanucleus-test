package org.apache.tomee.datanucleus.setup;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Ping", value = "/ping")
public class PingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse rsp)
            throws ServletException, IOException {
        rsp.setStatus(200);
        rsp.setContentType("text/plain");
        rsp.setCharacterEncoding("UTF-8");
        rsp.getWriter().write("here");
    }
}
