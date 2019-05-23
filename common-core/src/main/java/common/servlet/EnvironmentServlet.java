package common.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

public class EnvironmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.println("<html><head>");
        writer.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>");
        writer.println("<style type=\"text/css\">");
        writer.println("table { border-collapse: collapse; border-spacing: 2px 2px; line-height: 13pt; display: table; color: #333; }");
        writer.println("th{ background-color: #F0F0F0; border: #DDD solid  1px; min-width: 0.6em; padding: 5px; text-align: left; }");
        writer.println("td{border: #DDD solid  1px; min-width: 0.6em;padding: 5px;}");
        writer.println("</style>");
        writer.println("</head>\r\n<body>\r\n<table>");

        // Header
        writer.println("    <tr><th colspan=2>Headers</th></tr>");
        for (Enumeration<String> em = request.getHeaderNames(); em.hasMoreElements(); ) {
            String name = em.nextElement();
            writer.print("    <tr><td>" + name + "</td><td>");
            writer.println(request.getHeader(name) + "</td></tr>");
        }

        // Parameter

        // Request

        // Request Info
        writer.println("    <tr><th colspan=2>Request Info</th></tr>");
        writer.println("    <tr><td>Method</td><td>" + request.getMethod() + "</td></tr>");
        writer.println("    <tr><td>QueryString</td><td>" + request.getQueryString() + "</td></tr>");
        writer.println("    <tr><td>ContextPath</td><td>" + request.getContextPath() + "</td></tr>");
        writer.println("    <tr><td>PathInfo</td><td>" + request.getPathInfo() + "</td></tr>");
        writer.println("    <tr><td>PathTranslated</td><td>" + request.getPathTranslated() + "</td></tr>");
        writer.println("    <tr><td>RequestURI</td><td>" + request.getRequestURI() + "</td></tr>");
        writer.println("    <tr><td>RequestURL</td><td>" + request.getRequestURL() + "</td></tr>");
        writer.println("    <tr><td>ServletPath</td><td>" + request.getServletPath() + "</td></tr>");
        writer.println("    <tr><td>ContentType</td><td>" + request.getContentType() + "</td></tr>");
        writer.println("    <tr><td>ContentLength</td><td>" + request.getContentLength() + "</td></tr>");
        writer.println("    <tr><td>CharacterEncoding</td><td>" + request.getCharacterEncoding() + "</td></tr>");

        writer.println("    <tr><td>Locale</td><td>" + request.getLocale() + "</td></tr>");

        writer.println("    <tr><td>LocalAddr</td><td>" + request.getLocalAddr() + "</td></tr>");
        writer.println("    <tr><td>LocalName</td><td>" + request.getLocalName() + "</td></tr>");
        writer.println("    <tr><td>LocalPort</td><td>" + request.getLocalPort() + "</td></tr>");

        writer.println("    <tr><td>RemoteAddr</td><td>" + request.getRemoteAddr() + "</td></tr>");
        writer.println("    <tr><td>RemoteUser</td><td>" + request.getRemoteUser() + "</td></tr>");
        writer.println("    <tr><td>RemoteHost</td><td>" + request.getRemoteHost() + "</td></tr>");
        writer.println("    <tr><td>RemotePort</td><td>" + request.getRemotePort() + "</td></tr>");

        writer.println("    <tr><td>Protocol</td><td>" + request.getProtocol() + "</td></tr>");
        writer.println("    <tr><td>Scheme</td><td>" + request.getScheme() + "</td></tr>");
        writer.println("    <tr><td>ServerName</td><td>" + request.getServerName() + "</td></tr>");
        writer.println("    <tr><td>ServerPort</td><td>" + request.getServerPort() + "</td></tr>");

        // Context Info
        ServletContext context = request.getServletContext();
        writer.println("    <tr><th colspan=2>Context Info</th></tr>");
        writer.println("    <tr><td>Version</td><td>" + context.getMajorVersion() + "." + context.getMinorVersion() + "</td></tr>");
        writer.println("    <tr><td>ContextPath</td><td>" + context.getContextPath() + "</td></tr>");
        writer.println("    <tr><td>ServerInfo</td><td>" + context.getServerInfo() + "</td></tr>");
        writer.println("    <tr><td>ServletContextName</td><td>" + context.getServletContextName() + "</td></tr>");
        writer.println("    <tr><td>ResourcePaths</td><td>" + context.getResourcePaths("/") + "</td></tr>");

        // System Info
        writer.println("    <tr><th colspan=2>System Info</th></tr>");
        writer.println("    <tr><td>ENV</td><td>" + System.getenv() + "</td></tr>");

        writer.println("    <tr><th colspan=2>System Properties</th></tr>");
        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet())
            writer.println("    <tr><td>" + entry.getKey() + "</td><td>" + entry.getValue() + "</td></tr>");

        writer.println("</table></body></html>");
    }
}
