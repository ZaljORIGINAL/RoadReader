package roadManager.algorithms.dijkstra;

import mapObjects.Edge;

/**Клас предоставляет ячейку для рабты в алгоритме Дейкстрора.
 * т.е. Суммарная длина до конкретного узла и последний предшествующий узел*/
public class ShortestPath implements Comparable<ShortestPath>{
    private final long nodeId;
    private final double weight;
    private final long incomingEdgeId;

    public ShortestPath(long nodeId, double weight, long incomingEdgeId){
        this.nodeId = nodeId;
        this.weight = weight;
        this.incomingEdgeId = incomingEdgeId;
    }

    /**Вернет id точки от которой надо рассматривать следующие пути.*/
    public long getNodeId() {
        return nodeId;
    }

    public double getWeight() {
        return weight;
    }

    public long getIncomingEdgeId() {
        return incomingEdgeId;
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
