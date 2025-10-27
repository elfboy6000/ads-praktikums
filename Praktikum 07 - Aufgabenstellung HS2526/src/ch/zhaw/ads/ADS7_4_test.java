package ch.zhaw.ads;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @(#)TreeTest.java
 *
 *
 * @author K Rege
 * @version 1.00 2018/3/17
 * @version 1.01 2021/8/1
 */
public class ADS7_4_test {
    RouteServer routeServer;
    Graph<DijkstraNode, Edge> graph;
    String fileToTest = "RouteServer.java";

    @BeforeEach
    private void init() throws Exception {
        String swiss = "Winterthur Zürich 25\n" +
                "Zürich Bern 126\n" +
                "Zürich Genf 277\n" +
                "Zürich Luzern 54\n" +
                "Zürich Chur 121\n" +
                "Zürich Berikon 16\n" +
                "Bern Genf 155\n" +
                "Genf Lugano 363\n" +
                "Lugano Luzern 206\n" +
                "Lugano Chur 152\n" +
                "Chur Luzern 146\n" +
                "Luzern Bern 97\n" +
                "Bern Berikon 102\n" +
                "Luzern Berikon 41\n";
        routeServer = new RouteServer();
        graph = routeServer.createGraph(swiss);
    }

    private void testDest(List<DijkstraNode> route, String startName, String destName, double dist) {
        for (DijkstraNode town : route) {
            if (destName.equals(town.getName())) {
                assertEquals(dist, town.getDist(), 1E-10, startName + " to " + destName);
                return;
            }
        }
        fail(startName + " not connected to " + destName);
    }

    @Test
    public void testWinterthurLugano() {
        routeServer.dijkstraRoute(graph, "Winterthur", "Lugano");
        List<DijkstraNode> route = routeServer.getRoute(graph, "Lugano");
        testDest(route, "Winterthur", "Winterthur", 0);
        testDest(route, "Winterthur", "Zürich", 25);
        testDest(route, "Winterthur", "Luzern", 79);
        testDest(route, "Winterthur", "Lugano", 285);
    }

    @Test
    public void testLuganoWinterthur() {
        routeServer.dijkstraRoute(graph, "Lugano", "Winterthur");
        List<DijkstraNode> route = routeServer.getRoute(graph, "Winterthur");
        testDest(route, "Lugano", "Winterthur", 285);
        testDest(route, "Lugano", "Zürich", 260);
        testDest(route, "Lugano", "Luzern", 206);
        testDest(route, "Lugano", "Lugano", 0);
    }
}
