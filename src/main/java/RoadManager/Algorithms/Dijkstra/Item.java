package RoadManager.Algorithms.Dijkstra;

import MapObjects.Node;

/**Клас предоставляет ячейку для рабты в алгоритме Дейкстрора.
 * т.е. Суммарная длина до конкретного узла и последний предшествующий узел*/
public class Item {
    private final Node node;
    private final int length;
    private final Node lastNode;

    public Item(Node node, int length, Node lastNode){
        this.node = node;
        this.length = length;
        this.lastNode = lastNode;
    }

    public Node getPoint() {
        return node;
    }

    public int getLength() {
        return length;
    }

    public Node getLastPoint() {
        return lastNode;
    }
}
