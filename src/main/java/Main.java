import mapObjects.Node;
import mapObjects.OsmParser;
import mapObjects.Way;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import roadManager.Route;
import roadManager.algorithms.GeographicPoint;
import roadManager.algorithms.Graph;
import roadManager.algorithms.closestPointCalculator.ClosestPointCalculator;
import roadManager.algorithms.dijkstra.DijkstraAlgorithms;
import roadManager.algorithms.dijkstra.WeightTypes.LengthWeightCalculator;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

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

        //Алгоритм для поиска кратчайшего пути.
        DijkstraAlgorithms algorithm = new DijkstraAlgorithms(graph);

        //Географические точки
        //Точка: 1822701199
        GeographicPoint start = new GeographicPoint( 55.7424095, 52.3885373);
        //Точка: 5663181804
        GeographicPoint finish = new GeographicPoint( 55.7442759, 52.3763780);

        //Поиск для географических точек ближайшие tower точки.
        ClosestPointCalculator calculator = new ClosestPointCalculator(graph.getTowerNodes());
        Map<GeographicPoint, Node> closestNodes = calculator.calculate(Stream.of(start, finish).toList());

        Route route = algorithm.calculatePath(
                new LengthWeightCalculator(),
                closestNodes.get(start),
                closestNodes.get(finish));

        System.out.println(route.toString());
    }
}
