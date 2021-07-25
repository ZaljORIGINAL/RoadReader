package RoadManager;

import MapObjects.Point;

import java.util.List;
import java.util.Stack;

/**Класс представляет маршрут от стартовой точки до конечной*/
public class Route {
    private Stack<Point> points;
    private int length;

    public Route(Stack<Point> points, int length){
        this.points = points;
        this.length = length;
    }

    public Stack<Point> getPoints() {
        return points;
    }

    public int getLength() {
        return length;
    }
}
