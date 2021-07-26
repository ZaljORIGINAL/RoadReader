import MapObjects.Edge;
import MapObjects.Node;
import RoadManager.RoadManager;
import RoadManager.Route;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Node> nodes = RoadManager.getPoints();
        Map<Node, List<Edge>> graph = RoadManager.generateGraph(nodes);

        Route route = RoadManager.getShortestRoute(graph, nodes.get(1), nodes.get(9));
        System.out.println("Длина пути: " + route.getLength());
    }
}
