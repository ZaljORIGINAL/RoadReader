package RoadManager;

import MapObjects.Distance;
import MapObjects.Point;
import RoadManager.Algorithms.Algorithm;
import RoadManager.Algorithms.Dijkstra.DijkstraAlgorithms;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**Класс предоставляет методы для действий и вычислений над географическими точками.*/
public class RoadManager {
    public static Map<Integer, Point> getPoints(){
        Map<Integer, Point> points = new HashMap<>();

        points.put(1, new Point(1, "Point1"));
        points.put(2, new Point(2, "Point2"));
        points.put(3, new Point(3, "Point3"));
        points.put(4, new Point(4, "Point4"));
        points.put(5, new Point(5, "Point5"));
        points.put(6, new Point(6, "Point6"));
        points.put(7, new Point(7, "Point7"));
        points.put(8, new Point(8, "Point8"));
        points.put(9, new Point(9, "Point9"));

        return points;
    }

    public static Map<Integer, Point> connectPoints(Map<Integer, Point> points) {
            String[] distances = new String[]{
                    "1 2 10",
                    "1 3 6",
                    "1 4 8",
                    "2 4 5",
                    "2 5 13",
                    "2 7 11",
                    "3 5 3",
                    "4 3 2",
                    "4 5 5",
                    "4 7 12",
                    "5 6 9",
                    "5 9 12",
                    "6 8 8",
                    "6 9 10",
                    "7 6 4",
                    "7 8 6",
                    "7 9 16",
                    "8 9 15"};
            for (String str : distances) {
                var values = str.split(" ");
                var startId = Integer.parseInt(values[0]);
                var finishId = Integer.parseInt(values[1]);
                var weight = Integer.parseInt(values[2]);

                var startPoint = points.get(startId);
                var finishPoint = points.get(finishId);

                var distance = createDistance(startPoint, finishPoint, weight);
                startPoint.addDistance(distance);
                finishPoint.addDistance(distance);
            }

        return points;
    }

    public static Distance createDistance(Point start, Point finish, int length){
        return new Distance(start, finish, length);
    }

    public static Route getShortestRoute(Map<Integer, Point> points, Point start, Point finish){
        Algorithm algorithm = new DijkstraAlgorithms();
        return algorithm.calculatePath(points, start, finish);
    }
}
