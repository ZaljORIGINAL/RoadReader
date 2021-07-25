import MapObjects.Point;
import RoadManager.RoadManager;
import RoadManager.Route;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Integer, Point> points = RoadManager.getPoints();
        points = RoadManager.connectPoints(points);

        Route route = RoadManager.getShortestRoute(points, points.get(1), points.get(9));
        System.out.println("Длина пути: " + route.getLength());
    }
}
