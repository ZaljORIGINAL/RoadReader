package roadManager;

import mapObjects.Edge;
import mapObjects.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**Класс представляет маршрут от стартовой точки до конечной*/
public class Route {
    private Set<Node> nodes;
    //Длина измеряется в метрах.
    private double length;
    //Время измеряется в минутах.
    private double time;

    public Route(List<Edge> edges, double length, double speed){
        nodes = new HashSet<>();
        for (Edge edge : edges){
            List<Node> nodes = edge.getNodes();
            this.nodes.addAll(nodes);
        }
        this.length = length;
        this.time = length / (speed * 1000 / 60);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public double getLength() {
        return length;
    }

    public double getTime() {
        return time;
    }
}
