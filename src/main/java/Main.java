import MapObjects.Edge;
import MapObjects.Node;
import RoadManager.Algorithms.Graph;
import RoadManager.RoadManager;
import RoadManager.Route;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Node> nodes = RoadManager.getPoints();
        String[] edgesDescriptions = RoadManager.getEdgesDescriptions();

        Graph graph = new Graph(nodes, edgesDescriptions);

        Route route = RoadManager.getShortestRoute(graph, nodes.get(0), nodes.get(8));
        System.out.println("Длина пути: " + route.getLength());
    }
}
