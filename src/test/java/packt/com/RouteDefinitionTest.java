package packt.com;

import org.junit.Test;
import packt.com.router.RouteDefinition;

import static org.assertj.core.api.Assertions.assertThat;

public class RouteDefinitionTest {
    @Test
    public void testSimpleRoute() {
        RouteDefinition route = new RouteDefinition("GET /todos");

        assertThat(route.matches("GET", "/todos")).isEqualTo(true);
        assertThat(route.matches("POST", "/todos")).isEqualTo(false);
        assertThat(route.matches("PUT", "/todos/1")).isEqualTo(false);
    }

    @Test
    public void testRoutesWithParameteres(){
        RouteDefinition route = new RouteDefinition("POST /todos/:id");

        assertThat(route.matches("POST", "/todos/1")).isEqualTo(true);
        assertThat(route.matches("POST", "/todos/bar")).isEqualTo(true);
        assertThat(route.matches("POST", "/todos/1/foo")).isEqualTo(false);
        assertThat(route.matches("GET", "/todos/1")).isEqualTo(false);
        assertThat(route.matches("POST", "/todos")).isEqualTo(false);
    }
}
