import algorithms.pointsFinders.ClosestPointFinderTree;
import mapObjects.*;
import mapObjects.parseConfigurations.BlockedPointConfiguration;
import mapObjects.parseConfigurations.SpeedConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import algorithms.Graph;
import algorithms.pointsFinders.QuadTree;
import algorithms.dijkstra.DijkstraAlgorithms;
import algorithms.dijkstra.WeightTypes.LengthWeightCalculator;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, URISyntaxException {
        //Фаил карты
        URL urlToMap = Main.class.getResource("/toParse.osm");
        Path map = Paths.get(urlToMap.toURI());
        OsmParser parser = new OsmParser(map);

        //Фаил конфигурации скорости на дороге
        URL urlToMaxSpeedConfig = Main.class.getResource("/SpeedConfig.xml");
        Path maxSpeedConfigFile = Paths.get(urlToMaxSpeedConfig.toURI());
        SpeedConfiguration speedConfiguration = new
                SpeedConfiguration(maxSpeedConfigFile);

        //Фаил конфигурации заблокированных точек
        URL urlToBlockedPointConfig = Main.class.getResource("/BlockedPointConfigurations.xml");
        Path blockedPointConfig = Paths.get(urlToBlockedPointConfig.toURI());
        BlockedPointConfiguration blockedPointConfiguration = new
                BlockedPointConfiguration(blockedPointConfig);

        //Задаем парсеру модули конфигурации
        parser.setMaxSpeedConfiguration(speedConfiguration);
        parser.setBlockedPointConfiguration(blockedPointConfiguration);

        //Строим граф
        MapData mapData = parser.extract();
        Graph graph = new Graph(mapData.getNodes(), mapData.getEdges());

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
        mapData.getTowerNodes()
                .forEach(quadTree::add);

        Route route = algorithm.calculatePath(
                new LengthWeightCalculator(),
                (Node) quadTree.find(start),
                (Node) quadTree.find(finish));

        System.out.println(route.toString());
    }
}
