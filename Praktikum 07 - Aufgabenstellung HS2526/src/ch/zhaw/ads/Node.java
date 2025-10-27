package ch.zhaw.ads;

import java.util.LinkedList;
import java.util.List;

public class Node {
    protected String name; // Name
    protected List<Edge> edges; // Kanten

    public Node() {
        edges = new LinkedList<>();
    }

    public Node(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Iterable<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }
}
