package roadManager.algorithms.dijkstra;

import mapObjects.Node;
import mapObjects.OsmParser;
import mapObjects.OsmParserTest;
import mapObjects.Way;
import org.junit.Test;
import org.xml.sax.SAXException;
import roadManager.Route;
import roadManager.algorithms.Graph;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.Assert.*;

public class DijkstraAlgorithmsTest {

    @Test
    public void findingShortestRoute() throws ParserConfigurationException, IOException, SAXException {
        URL url = DijkstraAlgorithmsTest.class.getResource("/toParseSmal.osm");
        Path path = Paths.get(url.getPath());
        OsmParser parser = new OsmParser(path);
        Map<Long, Way> waysMap = parser.getWays();
        Graph graph = parser.convertToGraph(waysMap);

        DijkstraAlgorithms algorithms = new DijkstraAlgorithms(graph);
        Node start = new Node(523086937L, 55.7311784, 52.3950185);
        Node finish = new Node(835753789L, 55.7338274, 52.3938429);

        Route route = algorithms.calculatePath(start, finish);

        System.out.println("Расстояние: " + route.getLength());
        System.out.println("Время на преодоление: " + route.getTime());
        System.out.println("Двигайтесь по точкам: ");
        route.getNodes().stream()
                .forEach(node -> System.out.println("\tid: " + node.getId()));
    }
}