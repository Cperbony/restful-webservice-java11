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

    static {
        routes.put(new RouteDefinition("GET /todos"), TodosHandlers::listTodos);
        routes.put(new RouteDefinition("GET /todos/:id"), TodosHandlers::fetchTodo);
        routes.put(new RouteDefinition("POST /todos"), TodosHandlers::createTodoWithoutId);
        routes.put(new RouteDefinition("POST /todos/id"), TodosHandlers::createTodoWithId);
        routes.put(new RouteDefinition("PUT /todos/:id"), TodosHandlers::updateTodo);
        routes.put(new RouteDefinition("DELETE /todos/:id"), TodosHandlers::deleteTodo);
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
        for (Map.Entry<RouteDefinition, RouteHandler> route : routes.entrySet()) {
            if (route.getKey().matches(req)) {
                route.getValue().execute(req, resp);
                return;
            }
        }
        noMatchHandler(resp);
    }
}
