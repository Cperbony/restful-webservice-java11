package packt.com;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TodosServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    private Map<Long, Todo> todos = new HashMap<>();

    public TodosServlet() {
        todos.put(1L, new Todo(1L, "first Todo"));
        todos.put(2L, new Todo(2L, "second Todo"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = GSON.toJson(todos.values());
        resp.setStatus(200);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = readInputStream(req.getInputStream());
        Todo todo = GSON.fromJson(json, Todo.class);

        todos.put(todo.getId(), todo);

        resp.setStatus(201);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(GSON.toJson(todo));
    }

    private static String readInputStream(InputStream stream) {
        return new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining("\n"));
    }
}
