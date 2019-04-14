package packt.com;

import java.util.HashMap;
import java.util.Map;

public class Todos {

    public static Map<Long, Todo> todos = new HashMap<>();

    static  {
            todos.put(1L, new Todo(1L, "first Todo"));
            todos.put(2L, new Todo(2L, "second Todo"));
    }

}
