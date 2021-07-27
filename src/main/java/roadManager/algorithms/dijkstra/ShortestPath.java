package roadManager.algorithms.dijkstra;

import mapObjects.Edge;
import mapObjects.Node;

/**Клас предоставляет ячейку для рабты в алгоритме Дейкстрора.
 * т.е. Суммарная длина до конкретного узла и последний предшествующий узел*/
public class ShortestPath implements Comparable<ShortestPath>{
    private final Node node;
    private final double weight;
    private final Edge incomingEdge;

    public ShortestPath(Node node, double weight, Edge incomingEdge){
        this.node = node;
        this.weight = weight;
        this.incomingEdge = incomingEdge;
    }

    public Node getNode() {
        return node;
    }

    public double getWeight() {
        return weight;
    }

    public Edge getIncomingEdge() {
        return incomingEdge;
    }

    @Override
    public int compareTo(ShortestPath o) {

        if (weight < o.getWeight())
            return -1;
        else if (weight > o.getWeight())
            return 1;
        else
            return 0;
    }
}
