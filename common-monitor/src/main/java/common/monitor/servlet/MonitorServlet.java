package common.monitor.servlet;

import common.monitor.Cache;
import common.monitor.Monitor;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MonitorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();

        Cache cache = Monitor.getCache();

        cache.getValues().forEach((name, value) -> writer.println(makeName(name + "_Value") + "=" + value));

        cache.getTimers().forEach((name, timer) -> {
            writer.println(makeName(name + "_Count") + "=" + timer.getCount());
            writer.println(makeName(name + "_Duration") + "=" + timer.getDuration());
        });
    }

    private static String makeName(String name) {
        return StringUtils.replaceChars(name, ' ', '_');
    }
}
