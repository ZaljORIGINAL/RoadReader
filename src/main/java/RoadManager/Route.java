package RoadManager;

import MapObjects.Edge;
import MapObjects.Node;

import java.util.Stack;

/**Класс представляет маршрут от стартовой точки до конечной*/
public class Route {
    private Stack<Edge> nodes;
    private double length;

    public Route(Stack<Edge> nodes, double length){
        this.nodes = nodes;
        this.length = length;
    }

    public Stack<Edge> getNodes() {
        return nodes;
    }

    public double getLength() {
        return length;
    }
}
