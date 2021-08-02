import mapObjects.Node;
import mapObjects.OsmParser;
import mapObjects.Way;
import org.xml.sax.SAXException;
import roadManager.Route;
import roadManager.algorithms.Algorithm;
import roadManager.algorithms.Graph;
import roadManager.algorithms.dijkstra.DijkstraAlgorithms;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        URL url = Main.class.getResource("/toParse.osm");
        Path path = Paths.get(url.getPath());
        OsmParser parser = new OsmParser(path);
        Map<Long, Way> waysMap = parser.getWays();
        Graph graph = parser.convertToGraph(waysMap);

        //Поиск кратчайшего пути
        Algorithm algorithm = new DijkstraAlgorithms(graph);
        Node start = new Node(676981532L,  55.7413989, 52.4110127);
        Node finish = new Node(1420968855L,  55.7496950, 52.4204456);

        Route route = algorithm.calculatePath(start, finish);

        System.out.println("Расстояние: " + route.getLength());
        System.out.println("Время на преодоление: " + route.getTime());
        System.out.println("Двигайтесь по точкам: ");
        Set<Node> nodes = route.getNodes();
        nodes.stream()
                .forEach(node -> System.out.println("\tid: " + node.getId()));
    }
}
