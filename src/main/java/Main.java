import algorithms.closestPointCalculator.ClosestPointFinderTree;
import mapObjects.*;
import mapObjects.parseConfigurations.MaxSpeedConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import algorithms.Graph;
import algorithms.closestPointCalculator.QuadTree;
import algorithms.dijkstra.DijkstraAlgorithms;
import algorithms.dijkstra.WeightTypes.LengthWeightCalculator;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, URISyntaxException {
        URL urlToMap = Main.class.getResource("/toParse.osm");
        Path map = Paths.get(urlToMap.toURI());
        OsmParser parser = new OsmParser(map);

        URL urlToMaxSpeedConfig = Main.class.getResource("/MaxSpeedConfig.xml");
        Path maxSpeedConfigFile = Paths.get(urlToMaxSpeedConfig.toURI());
        MaxSpeedConfiguration maxSpeedConfiguration = new
                MaxSpeedConfiguration(maxSpeedConfigFile);

        parser.setMaxSpeedConfiguration(maxSpeedConfiguration);

        Set<Way> ways = parser.getWays();
        LOGGER.info("Количество полученных путей: " + ways.size());
        Graph graph = parser.convertToGraph(ways);
        LOGGER.info("Граф выстроен...");

        //Алгоритм для поиска кратчайшего пути.
        DijkstraAlgorithms algorithm = new DijkstraAlgorithms(graph);

        //Географические точки
        //Точка: 1822701199
        GeographicPoint start = new GeographicPoint( 55.7424095, 52.3885373);
        //Точка: 5663181804
        GeographicPoint finish = new GeographicPoint( 55.7442759, 52.3763780);

        //Поиск для географических точек ближайшие tower точки.
        QuadTree quadTree = new ClosestPointFinderTree(
                55.63213647883612, 52.201261423294824,
                55.81003367465946, 52.605008981888574,
                6);
        graph.getTowerNodes().forEach(quadTree::add);

        Route route = algorithm.calculatePath(
                new LengthWeightCalculator(),
                (Node) quadTree.find(start),
                (Node) quadTree.find(finish));

        System.out.println(route.toString());
    }
}
