package roadManager;

import mapObjects.Edge;
import mapObjects.Node;
import roadManager.algorithms.Algorithm;
import roadManager.algorithms.dijkstra.DijkstraAlgorithms;
import roadManager.algorithms.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Класс предоставляет методы для действий и вычислений над географическими точками.*/
public class RoadManager {
    public static Map<Node, List<Edge>> generateGraph(List<Node> nodes, String[] edgesDescriptions) {
        Map<Node, List<Edge>> graph = new HashMap<>();
        for (Node node : nodes) {
            graph.put(node, new ArrayList<Edge>());
        }

        for (String str : edgesDescriptions) {
            String[] values = str.split(" ");
            int startId = Integer.parseInt(values[0]);
            int finishId = Integer.parseInt(values[1]);
            int weight = Integer.parseInt(values[2]);

            Node startPoint = getNodeById(nodes, startId);
            if (startPoint == null)
                break;
            Node finishPoint = getNodeById(nodes, finishId);
            if (finishPoint == null)
                break;

/*            Edge distance = new Edge(startPoint, finishPoint, weight);
            graph.get(startPoint).add(distance);
            graph.get(finishPoint).add(distance);*/
        }

        return graph;
    }

    public static Node getNodeById(List<Node> nodes, int id){
        for (Node node : nodes) {
            if (node.getId() == id)
                return node;
        }

        return null;
    }

    public static Route getShortestRoute(Graph graph, Node start, Node finish){
        Algorithm algorithm = new DijkstraAlgorithms(graph);
        return algorithm.calculatePath(start, finish);
    }

    public static String[] getEdgesDescriptions(){
        return new String[]{
                "1 2 10",
                "1 3 6",
                "1 4 8",
                "2 4 5",
                "2 5 13",
                "2 7 11",
                "3 5 3",
                "4 3 2",
                "4 5 5",
                "4 7 12",
                "5 6 9",
                "5 9 12",
                "6 8 8",
                "6 9 10",
                "7 6 4",
                "7 8 6",
                "7 9 16",
                "8 9 15"};
    }
}
