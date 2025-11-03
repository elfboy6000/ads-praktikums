package ch.zhaw.ads;

import java.util.*;

public class RouteServer implements CommandExecutor {
    /**
     * build the graph given a text file with the topology
     */
    public Graph<DijkstraNode, Edge> createGraph(String topo) {
        Graph<DijkstraNode, Edge> graph = new AdjListGraph<>(DijkstraNode.class, Edge.class);

        for (String line : topo.split("\n")) {
            if (line.isBlank()) continue;

            String[] parts = line.trim().split("\\s");
            String from = parts[0];
            String to = parts[1];
            double weight = Double.parseDouble(parts[2]);

            try {
                graph.addEdge(from, to, weight);
                graph.addEdge(to, from, weight);
            } catch (Throwable e) {
                throw new RuntimeException("Fehler beim Einfügen der Kante: " + from + " - " + to, e);
            }
        }


        return graph;
    }


    /**
     * apply the dijkstra algorithm
     */
    public void dijkstraRoute(Graph<DijkstraNode, Edge> graph, String from, String to) {
        for (DijkstraNode town : graph.getNodes()) {
            town.setDist(Double.POSITIVE_INFINITY);
            town.setPrev(null);
            town.setMark(false);
        }

        DijkstraNode start = graph.findNode(from);
        start.setDist(0);

        PriorityQueue<DijkstraNode> pq = new PriorityQueue<>();
        pq.add(start);

        while (!pq.isEmpty()) {
            DijkstraNode current = pq.poll();
            if (current.getMark()) {
                continue;
            }
            current.setMark(true);

            for (Edge edge : current.getEdges()) {
                DijkstraNode dest = (DijkstraNode) edge.getDest();
                double newDist = current.getDist() + edge.getWeight();
                if (newDist < dest.getDist()) {
                    dest.setDist(newDist);
                    dest.setPrev(current);
                    pq.add(dest);
                }

            }

        }
    }

    /**
     * find the route in the graph after applied dijkstra
     * the route should be returned with the start town first
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

    public String execute(String topo) {
        Graph<DijkstraNode, Edge> graph = createGraph(topo);
        dijkstraRoute(graph, "Winterthur", "Lugano");
        List<DijkstraNode> route = getRoute(graph, "Lugano");
        // generate result string
        StringBuilder builder = new StringBuilder();
        for (DijkstraNode rt : route) builder.append(rt).append("\n");
        return builder.toString();
    }

    public static void main(String[] args) {
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
