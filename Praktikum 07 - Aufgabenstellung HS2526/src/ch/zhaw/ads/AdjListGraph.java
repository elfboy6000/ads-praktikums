package ch.zhaw.ads;

import java.util.LinkedList;
import java.util.List;

public class AdjListGraph<N extends Node, E extends Edge>
        implements Graph<N, E> {
    private final List<N> nodes = new LinkedList<>();
    private final Class<N> nodeClazz;
    private final Class<E> edgeClazz;

    public AdjListGraph(Class<N> nodeClazz, Class<E> edgeClazz) {
        this.nodeClazz = nodeClazz;
        this.edgeClazz = edgeClazz;
    }

    /** füge Knoten hinzu, gebe alten zurück falls Knoten schon existiert */
    public N addNode(String name) throws Throwable {
        N node = findNode(name);
        if (node == null) {
            node = nodeClazz.getConstructor().newInstance();
            node.setName(name);
            nodes.add(node);
        }
        return node;
    }

    /** füge gerichtete Kante hinzu */
    public void addEdge(String source, String dest, double weight) throws Throwable {
        N src = addNode(source);
        N dst = addNode(dest);

        E edge = edgeClazz.getConstructor().newInstance();
        edge.setDest(dst);
        edge.setWeight(weight);
        src.addEdge(edge);
    }

    /** finde den Knoten anhand seines Namens */
    public N findNode(String name) {
        for (N node : nodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    /** Iterator über alle Knoten */
    public Iterable<N> getNodes() {
        return nodes;
    }
}
