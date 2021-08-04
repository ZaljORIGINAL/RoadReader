import mapObjects.Node;
import mapObjects.OsmParser;
import mapObjects.Way;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import roadManager.Route;
import roadManager.algorithms.Graph;
import roadManager.algorithms.dijkstra.DijkstraAlgorithms;
import roadManager.algorithms.dijkstra.WeightTypes.LengthWeightCalculator;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, URISyntaxException {
        URL url = Main.class.getResource("/toParseSmall.osm");
        Path path = Paths.get(url.toURI());
        OsmParser parser = new OsmParser(path);
        Map<Long, Way> waysMap = parser.getWays();
        LOGGER.info("Количество полученных путей: " + waysMap.size());
        Graph graph = parser.convertToGraph(waysMap);
        LOGGER.info("Граф выстроен...");

        //Поиск кратчайшего пути
        DijkstraAlgorithms algorithm = new DijkstraAlgorithms(graph);
        Node start = new Node(667759139L, 55.7382326, 52.3858329);
        Node finish = new Node(516329569L, 55.7392678, 52.3792657);

        Route route = algorithm.calculatePath(new LengthWeightCalculator(), start, finish);
        System.out.println(route.toString());
    }
}
