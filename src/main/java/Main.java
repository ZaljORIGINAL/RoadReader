import mapObjects.Node;
import mapObjects.OsmParser;
import mapObjects.Way;
import org.xml.sax.SAXException;
import roadManager.algorithms.Graph;
import roadManager.RoadManager;
import roadManager.Route;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        URL url = Main.class.getResource("/wayDataTest.osm");
        Path path = Paths.get(url.getPath());
        OsmParser parser = new OsmParser(path);
        Map<Integer, Way> waysMap = parser.getWays();

        List<Node> nodes = RoadManager.getPoints();
        String[] edgesDescriptions = RoadManager.getEdgesDescriptions();

        Graph graph = new Graph(nodes, edgesDescriptions);

        Route route = RoadManager.getShortestRoute(graph, nodes.get(0), nodes.get(8));
        System.out.println("Длина пути: " + route.getLength());
    }
}
