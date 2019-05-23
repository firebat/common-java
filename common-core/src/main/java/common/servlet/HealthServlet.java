package common.servlet;

import common.management.ServerManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HealthServlet extends HttpServlet {

    private ServerManager manager = ServerManager.get();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setStatus(manager.isHealth() ? HttpServletResponse.SC_OK : HttpServletResponse.SC_NOT_FOUND);
    }
}
