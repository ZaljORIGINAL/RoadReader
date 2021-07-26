package RoadManager.Algorithms.Dijkstra;

import MapObjects.Node;

/**Клас предоставляет ячейку для рабты в алгоритме Дейкстрора.
 * т.е. Суммарная длина до конкретного узла и последний предшествующий узел*/
public class TotalPath implements Comparable<TotalPath>{
    private final Node node;
    private final int length;
    private final Node lastNode;

    public TotalPath(Node node, int length, Node lastNode){
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

    /*FIXME Проверить работоспособность сортировки
    *  Элементы сдлиной -1 должны прокидоваться назад*/
    @Override
    public int compareTo(TotalPath o) {
        if (length == -1)
            return +1;

        return length - o.getLength();
    }
}
