import algorithms.Graph;
import algorithms.dijkstra.DijkstraAlgorithms;
import algorithms.dijkstra.WeightTypes.DistanceWeighting;
import algorithms.dijkstra.WeightTypes.TimeWeighting;
import algorithms.dijkstra.WeightСalculator;
import algorithms.pointsFinders.ClosestPointFinderTree;
import algorithms.pointsFinders.QuadTree;
import mapObjects.GeographicPoint;
import mapObjects.Node;
import mapObjects.OsmParser;
import mapObjects.Route;
import mapObjects.parseConfigurations.BlockedPointConfiguration;
import mapObjects.parseConfigurations.SpeedConfiguration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Application {
    private final Graph graph;
    private final QuadTree nodeTree;
    private final DijkstraAlgorithms algorithm;

    public Application(Path osmFile, double minLat, double minLon, double maxLat, double maxLon, Path speedConfigFile) throws ParserConfigurationException, IOException, SAXException {

        SpeedConfiguration speedConfig;
        if (speedConfigFile == null)
            speedConfig = new SpeedConfiguration();
        else {
            try {
                speedConfig = new SpeedConfiguration(speedConfigFile);
            } catch (ParserConfigurationException | IOException | SAXException exception) {
                speedConfig = new SpeedConfiguration();
            }
        }

        OsmParser parser = new OsmParser(osmFile);
        parser.setSpeedConfiguration(speedConfig);
        graph = parser.toGraph();
        nodeTree = new ClosestPointFinderTree(minLat, minLon, maxLat, maxLon, 6);
        graph.getNodes().forEach(nodeTree::add);
        algorithm = new DijkstraAlgorithms(graph);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        DijkstraAlgorithms algorithms = new DijkstraAlgorithms(graph);
        boolean isRunning = true;
        while (isRunning) {
            //Запрашиваем географические точки
            GeographicPoint start = requestGeographicPoint(scanner ,"Введите данные по стартовой точке:");
            GeographicPoint finish = requestGeographicPoint(scanner ,"Введите данные по конечной точке точке:");

            //Вибор типа поиска (наикратчайший путь, наибыстрейший путь, оптимальнейший путь)
            WeightСalculator weighting = getWeighting(scanner);

            //Запрос на заблокированные точки
            BlockedPointConfiguration blockedPointConfig = getBlockedPoints(scanner);
            algorithm.setBlockedPointConfiguration(blockedPointConfig);

            Route route = algorithm.calculateRoute(
                    weighting,
                    (Node) nodeTree.find(start),
                    (Node) nodeTree.find(finish));

            if (route != null)
                printRoute(route);
            else
                System.out.println("ОШИБКА! Недулось построить маршрут!");

            isRunning = repeatRequest(scanner);
        }
    }

    private GeographicPoint requestGeographicPoint(Scanner scanner, String message) {
        System.out.println(message);
        System.out.print("\tВведите широту: ");
        double lat = Double.parseDouble(
                scanner.nextLine());
        System.out.print("\tВведите долготу: ");
        double lon = Double.parseDouble(
                scanner.nextLine());
        return new GeographicPoint(lat, lon);
    }

    private WeightСalculator getWeighting(Scanner scanner) {
        System.out.println("Найти маршрут по типу (shortest, fastest, optimal): ");
        switch (scanner.nextLine()) {
            case "shortest": {
                return new DistanceWeighting();
            }

            case "fastest": {
                return new TimeWeighting();
            }

/*            case "optimal": {

            }break;*/

            default: {
                return new DistanceWeighting();
            }
        }
    }

    private BlockedPointConfiguration getBlockedPoints(Scanner scanner) {
        System.out.print("Путь к конфигурационному файлу заблокированных точек: ");
        try {
            Path blockedPointsConfig = Paths.get(
                    Application.class.getResource(scanner.nextLine()).toURI());
            return new BlockedPointConfiguration(blockedPointsConfig);
        } catch (URISyntaxException
                | ParserConfigurationException
                | IOException
                | SAXException exception) {
            System.out.println("ОШИБКА! Неудалось считать фаил конфигурации заблокированных точек.");
            return new BlockedPointConfiguration();
        }
    }

    private boolean repeatRequest(Scanner scanner) {
        System.out.print("Завершить работу [Y/N]?");
        return scanner.nextLine().equals("Y");
    }

    private void printRoute(Route route) {
        System.out.println("Маршрут построен:");
        System.out.println(route.toString());
    }
}
