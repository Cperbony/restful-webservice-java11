package packt.com.database;

import org.dalesbred.Database;

public class DB {

    public static final Database db;

    static {
        db = Database.forUrlAndCredentials(
                "jdbc:postgres://[::1]:5432/todo-api", "todo-api", "123");
    }

}
