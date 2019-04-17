package packt.com.router;

import packt.com.todo.TodosHandlers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RouteServlet extends HttpServlet {

    private static final Map<RouteDefinition, RouteHandler> routes = new HashMap<>();

    private static void addRoute(String route, RouteHandler handler) {
        routes.put(new RouteDefinition(route), handler);
    }

    static {
        addRoute("GET /todos", TodosHandlers::listTodos);
        addRoute("GET /todos/:id", TodosHandlers::fetchTodo);
        addRoute("POST /todos", TodosHandlers::createTodoWithoutId);
        addRoute("POST /todos/id", TodosHandlers::createTodoWithId);
        addRoute("PUT /todos/:id", TodosHandlers::updateTodo);
        addRoute("DELETE /todos/:id", TodosHandlers::deleteTodo);

        addRoute("GET /hello/:username", (req, resp) -> {
            String username = req.getRequestURI().substring("/hello/".length());
            resp.setStatus(200);
            resp.getWriter().println("Hello " + username);
        });
    }

    /*
     * Map<RouteDefinition, RouteHandler>
     * "GET /todos"
     * "POST /todos/:id"
     *
     * RouteDefinition
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        genericHandler(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        genericHandler(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        genericHandler(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        genericHandler(req, resp);
    }

    protected void noMatchHandler(HttpServletResponse resp) throws IOException {
        // no routes match
        resp.setStatus(404);
        resp.getWriter().println("Route not Found!");
    }

    private void genericHandler(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getHeader("Authorization");

        //TODO in actual app. fetch from database
        String actualToken = "even_more_secret_stuff";
        boolean isVerified = actualToken.equals(token);

        if(!isVerified){
            resp.getWriter().println("Sorry, authentication required.");
            resp.setStatus(401);
            return;
        }

        for (Map.Entry<RouteDefinition, RouteHandler> route : routes.entrySet()) {
            if (route.getKey().matches(req)) {
                route.getValue().execute(req, resp);
                return;
            }
        }
        noMatchHandler(resp);
    }
}
