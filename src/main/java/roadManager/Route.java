package roadManager;

import mapObjects.Edge;

import java.util.List;
import java.util.Stack;

/**Класс представляет маршрут от стартовой точки до конечной*/
public class Route {
    private List<Edge> nodes;
    private double length;

    public Route(List<Edge> nodes, double length){
        this.nodes = nodes;
        this.length = length;
    }

    public List<Edge> getNodes() {
        return nodes;
    }

    public double getLength() {
        return length;
    }
}
