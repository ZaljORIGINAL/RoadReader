package RoadManager;

import MapObjects.Node;

import java.util.Stack;

/**Класс представляет маршрут от стартовой точки до конечной*/
public class Route {
    private Stack<Node> nodes;
    private int length;

    public Route(Stack<Node> nodes, int length){
        this.nodes = nodes;
        this.length = length;
    }

    public Stack<Node> getNodes() {
        return nodes;
    }

    public int getLength() {
        return length;
    }
}
