package ch.zhaw.ads;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @(#)TreeTest.java
 *
 *
 * @author K Rege
 * @version 1.00 2018/3/17
 * @version 1.01 2021/8/1
 */
public class ADS7_3_test {
    RouteServer routeServer;
    Graph<DijkstraNode, Edge> graph;
    String fileToTest = "RouteServer.java";

    @BeforeEach
    void init() throws Exception {
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

    private void testDest(DijkstraNode start, String destName, double dist) {
        for (Edge road: start.getEdges()) {
            DijkstraNode destTown = (DijkstraNode) road.getDest();
            if (destName.equals(destTown.getName())) {
                assertEquals(dist, road.getWeight(), 1E-10, start.getName() + " to " + destName);
                return;
            }
        }
        fail(destName + " not connected to " + start.getName());
    }

    @Test
    public void testCreateGrasphEdges() {
        DijkstraNode town = graph.findNode("Luzern");
        assertNotNull(town, "Luzern");
        testDest(town, "Bern", 97);
        testDest(town, "Berikon", 41);
        testDest(town, "Chur", 146);
        testDest(town, "Lugano", 206);
        testDest(town, "Zürich", 54);
        testDest(town, "Berikon", 41);
    }

    @Test
    public void testCreateGrasphNodes() {
        DijkstraNode town = graph.findNode("Luzern");
        assertNotNull(town, "Luzern");
        town = graph.findNode("Winterthur");
        assertNotNull(town, "Winterthur");
        town = graph.findNode("Paris");
        assertNull(town, "Paris");
    }
}
