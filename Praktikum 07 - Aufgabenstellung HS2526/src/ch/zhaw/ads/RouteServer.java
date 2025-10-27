package ch.zhaw.ads;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class RouteServer implements CommandExecutor {
    /**
    build the graph given a text file with the topology
    */
    public Graph<DijkstraNode> createGraph(String topo) throws Exception {
        // TODO implement
    }


    /**
    apply the dijkstra algorithm
    */
    public void dijkstraRoute(Graph<DijkstraNode> graph, String from, String to) {
        // TODO implement
    }

    /**
    find the route in the graph after applied dijkstra
    the route should be returned with the start town first
    */
    public List<DijkstraNode> getRoute(Graph<DijkstraNode, Edge> graph, String to) {
        List<DijkstraNode> route = new LinkedList<>();
        DijkstraNode town = graph.findNode(to);
        do {
            route.add(0, town);
            town = town.getPrev();
        } while (town != null);
        return route;
    }

    public String execute(String topo) throws Exception {
        Graph<DijkstraNode, Edge> graph = createGraph(topo);
        dijkstraRoute(graph, "Winterthur", "Lugano");
        List<DijkstraNode> route = getRoute(graph, "Lugano");
        // generate result string
        StringBuilder builder = new StringBuilder();
        for (DijkstraNode rt : route) builder.append(rt).append("\n");
        return builder.toString();
    }

    public static void main(String[] args)throws Exception {
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
        RouteServer server = new RouteServer();
        System.out.println(server.execute(swiss));
    }
}
