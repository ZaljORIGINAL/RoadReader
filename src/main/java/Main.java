import mapObjects.Node;
import roadManager.algorithms.Graph;
import roadManager.RoadManager;
import roadManager.Route;

import java.util.List;

/*
* https://github.com/rovaniemi/osm-graph-parser
* https://wiki.openstreetmap.org/wiki/OSM_XML
* https://javarush.ru/groups/posts/656-konkurs-osnovih-xml-dlja-java-programmista---chastjh-31-iz-3---dom
*
* */

public class Main {
    public static void main(String[] args) {
        List<Node> nodes = RoadManager.getPoints();
        String[] edgesDescriptions = RoadManager.getEdgesDescriptions();

        Graph graph = new Graph(nodes, edgesDescriptions);

        Route route = RoadManager.getShortestRoute(graph, nodes.get(0), nodes.get(8));
        System.out.println("Длина пути: " + route.getLength());
    }
}
