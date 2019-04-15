package packt.com.todo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import packt.com.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TodosHandlers {

    private static final Gson GSON = new GsonBuilder().create();

    public static void listTodos(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String json = GSON.toJson(Todos.todos.values());

        resp.setStatus(200);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(json);
    }

    public static void fetchTodo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        Long id = Long.parseLong(uri.substring("/todos/".length()));

        String json = GSON.toJson(Todos.todos.get(id));

        resp.setStatus(200);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(json);
    }


    public static void createTodoWithoutId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = Util.readInputStream(req.getInputStream());
        Todo todo = GSON.fromJson(json, Todo.class);

        todo.setId(Todos.nextId());

        Todos.todos.put(todo.getId(), todo);

        resp.setStatus(201);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(GSON.toJson(todo));
    }

    public static void createTodoWithId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = Util.readInputStream(req.getInputStream());
        Todo todo = GSON.fromJson(json, Todo.class);

        todo.setId(Todos.nextId());

        Todos.todos.put(todo.getId(), todo);

        resp.setStatus(201);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(GSON.toJson(todo));
    }

    public static void updateTodo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        Long id = Long.parseLong(uri.substring("/todos/".length()));

        if (!Todos.todos.containsKey(id)) {
            resp.setStatus(422);
            resp.getOutputStream().println("You cannot update TODO with id " + id + " because it doesn't exists!");
        }

        String json = Util.readInputStream(req.getInputStream());
        Todo todo = GSON.fromJson(json, Todo.class);
        todo.setId(id);

        Todos.todos.put(todo.getId(), todo);

        resp.setStatus(200);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(GSON.toJson(json));
    }


    public static void deleteTodo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        Long id = Long.parseLong(uri.substring("/todos/".length()));

        Todo todo = Todos.todos.remove(id);
        String json = GSON.toJson(todo);

        resp.setStatus(200);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(json);
    }
}

