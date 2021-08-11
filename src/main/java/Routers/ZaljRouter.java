package Routers;

import algorithms.Graph;
import algorithms.dijkstra.DijkstraAlgorithms;
import algorithms.dijkstra.WeightTypes.DistanceWeighting;
import algorithms.dijkstra.WeightTypes.OptimalWeighting;
import algorithms.dijkstra.WeightTypes.TimeWeighting;
import algorithms.dijkstra.WeightСalculator;
import algorithms.pointsFinders.QuadTree;
import mapObjects.GeographicPoint;
import mapObjects.OsmParser;
import mapObjects.Route;
import mapObjects.parseConfigurations.BlockedPointConfiguration;
import mapObjects.parseConfigurations.SpeedConfiguration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static Routers.CommandsHelper.*;

public class ZaljRouter extends Router {
    private final Graph graph;
    private final QuadTree nodeTree;
    private final DijkstraAlgorithms algorithm;

    public ZaljRouter(Path osmFile, double minLat, double minLon, double maxLat, double maxLon, Path speedConfigFile) throws ParserConfigurationException, IOException, SAXException {
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
        algorithm.setBlockedPointConfiguration(new BlockedPointConfiguration());
    }

    @Override
    public String getName() {
        return ZALJ_ROUTER;
    }

    @Override
    protected void callHelpList() {
        System.out.println("Потдерживаемые команды в модуле: ");

        //
        System.out.println("\tКоманда " + GET_ROUTE_COMMAND + "\n" +
                "\tОписание: Найти маршрут от точки A до точки B.\n" +
                "\tПараметры: [0]параметр поиска [1]точка старта [2]точка финиша.\n" +
                "\tПример: " + GET_ROUTE_COMMAND + " shortest 55.7439189,52.4248362 55.7513027,52.4241970\n");

        System.out.println("\tКоманда " + BLOCKED_POINTS_COMMAND + "\n" +
                "\tОписание: Задать список точек заблокированных точек для движения.\n" +
                "\tПараметры: [0]Путь к файлу\n" +
                "\tПример: " + GET_ROUTE_COMMAND + " /home/user/blockedPoints.xml");
    }

    @Override
    protected void callCommand(String commandName, String[] args) {
        switch (commandName) {
            case GET_ROUTE_COMMAND: {
                try {
                    WeightСalculator weighting = getWeighting(args[0]);
                    GeographicPoint startPoint = getGeographicPoint(args[1]);
                    GeographicPoint finishPoint = getGeographicPoint(args[2]);
                    calcRout(weighting, startPoint, finishPoint);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } break;

            case BLOCKED_POINTS_COMMAND: {
                try {
                    setBlockedPoints(args[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }break;

            default:
        }
    }

    private GeographicPoint getGeographicPoint(String coordinates) throws IOException {
        String[] coords = coordinates.split(",");
        return new GeographicPoint(
                Double.parseDouble(coords[0]),
                Double.parseDouble(coords[1]));
    }

    private WeightСalculator getWeighting(String weightingType) throws IOException {
        switch (weightingType) {
            case SHORTEST_WEIGHTING: {
                return new DistanceWeighting();
            }

            case FASTEST_WEIGHTING: {
                return new TimeWeighting();
            }

            case OPTIMAL_WEIGHTING: {
                return new OptimalWeighting();
            }

            default: {
                return new DistanceWeighting();
            }
        }
    }

    private void printRoute(Route route) {
        System.out.println("Маршрут построен:");
        System.out.println(route.toString());
    }

    private void calcRout(WeightСalculator weighting, GeographicPoint startPoint, GeographicPoint finishPoint) {
        Route route = algorithm.calculateRoute(
                weighting,
                nodeTree.getClosestPoint(startPoint),
                nodeTree.getClosestPoint(finishPoint));

        if (route != null)
            printRoute(route);
        else
            System.out.println("ОШИБКА! Не удалось построить маршрут!");
    }

    private void setBlockedPoints(String path) throws IOException {
        try {
            Path blockedPointsConfig = Paths.get(path);
            algorithm.setBlockedPointConfiguration(new BlockedPointConfiguration(blockedPointsConfig, nodeTree));
        } catch (ParserConfigurationException
                | SAXException exception) {
            System.out.println("ОШИБКА! Неудалось считать фаил конфигурации заблокированных точек.");
        }
    }
}
