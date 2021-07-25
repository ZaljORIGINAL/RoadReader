package RoadManager.Algorithms.Dijkstra;

import MapObjects.Point;

/**Клас предоставляет ячейку для рабты в алгоритме Дейкстрора.
 * т.е. Суммарная длина до конкретного узла и последний предшествующий узел*/
public class Item {
    private final Point point;
    private final int length;
    private final Point lastPoint;

    public Item(Point point, int length, Point lastPoint){
        this.point = point;
        this.length = length;
        this.lastPoint = lastPoint;
    }

    public Point getPoint() {
        return point;
    }

    public int getLength() {
        return length;
    }

    public Point getLastPoint() {
        return lastPoint;
    }
}
