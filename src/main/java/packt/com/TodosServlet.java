package packt.com;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class TodosServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = GSON.toJson(Todos.todos.values());
        resp.setStatus(200);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = readInputStream(req.getInputStream());
        Todo todo = GSON.fromJson(json, Todo.class);

        Todos.todos.put(todo.getId(), todo);

        resp.setStatus(201);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(GSON.toJson(todo));
    }

    private static String readInputStream(InputStream stream) {
        return new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining("\n"));
    }
}
