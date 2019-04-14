package packt.com.router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RouteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        if (uri.startsWith("/hello")) {
            resp.setStatus(200);
            resp.getWriter().println("/hello");
        } else if (uri.startsWith("/goodbye")) {
            resp.setStatus(200);
            resp.getWriter().println("Goodbyyyyyyyyye !");
        }
    }
}
