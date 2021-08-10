import algorithms.Graph;
import algorithms.dijkstra.DijkstraAlgorithms;
import algorithms.dijkstra.WeightTypes.DistanceWeighting;
import algorithms.dijkstra.WeightTypes.TimeWeighting;
import algorithms.dijkstra.WeightСalculator;
import algorithms.pointsFinders.QuadTree;
import mapObjects.GeographicPoint;
import mapObjects.Node;
import mapObjects.OsmParser;
import mapObjects.Route;
import mapObjects.parseConfigurations.BlockedPointConfiguration;
import mapObjects.parseConfigurations.SpeedConfiguration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {
    private final Graph graph;
    private final QuadTree nodeTree;
    private final DijkstraAlgorithms algorithm;
    private final BlockedPointConfiguration blockedPointConfiguration;

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
        nodeTree = new QuadTree(minLat, minLon, maxLat, maxLon, 6);
        graph.getTowerNodes().forEach(nodeTree::add);
        algorithm = new DijkstraAlgorithms(graph);
        blockedPointConfiguration =
            new BlockedPointConfiguration();
    }

    public void run() throws IOException {
        BufferedReader keyReader = new BufferedReader(new InputStreamReader(System.in));
        boolean isRunning = true;
        while (isRunning) {
            //Запрашиваем географические точки
            GeographicPoint start = requestGeographicPoint(keyReader ,"Введите данные по стартовой точке:");
            GeographicPoint finish = requestGeographicPoint(keyReader ,"Введите данные по конечной точке точке:");

            //Вибор типа поиска (наикратчайший путь, наибыстрейший путь, оптимальнейший путь)
            WeightСalculator weighting = getWeighting(keyReader);

            //Запрос на заблокированные точки
            BlockedPointConfiguration blockedPointConfig = getBlockedPoints(keyReader);
            algorithm.setBlockedPointConfiguration(blockedPointConfig);

            Route route = algorithm.calculateRoute(
                    weighting,
                    nodeTree.getClosestPoint(start),
                    nodeTree.getClosestPoint(finish));

            if (route != null)
                printRoute(route);
            else
                System.out.println("ОШИБКА! Недулось построить маршрут!");

            isRunning = repeatRequest(keyReader);
        }
    }

    private GeographicPoint requestGeographicPoint(BufferedReader bufferedReader, String message) throws IOException {
        System.out.println(message);
        System.out.print("\tВведите широту долготу: ");
        String[] values = bufferedReader.readLine().split(" ");
        double lat = Double.parseDouble(
                values[0]);
        double lon = Double.parseDouble(
                values[1]);
        return new GeographicPoint(lat, lon);
    }

    private WeightСalculator getWeighting(BufferedReader bufferedReader) throws IOException {
        System.out.print("Найти маршрут по типу (shortest, fastest, optimal): ");
        switch (bufferedReader.readLine()) {
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

    private BlockedPointConfiguration getBlockedPoints(BufferedReader bufferedReader) throws IOException {
        System.out.print("Путь к конфигурационному файлу заблокированных точек: ");
        try {
            Path blockedPointsConfig = Paths.get(bufferedReader.readLine());
            return new BlockedPointConfiguration(blockedPointsConfig, nodeTree);
        } catch (ParserConfigurationException
                | SAXException exception) {
            System.out.println("ОШИБКА! Неудалось считать фаил конфигурации заблокированных точек.");
            return blockedPointConfiguration;
        }
    }

    private boolean repeatRequest(BufferedReader bufferedReader) throws IOException {
        System.out.print("Завершить работу [Y/N]? ");
        return bufferedReader.readLine().equals("Y");
    }

    private void printRoute(Route route) {
        System.out.println("Маршрут построен:");
        System.out.println(route.toString());
    }
}
