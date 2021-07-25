package RoadManager;

import MapObjects.Distance;
import MapObjects.Point;
import RoadManager.Algorithms.Algorithm;
import RoadManager.Algorithms.Dijkstra;

import java.util.ArrayList;

/**Класс предоставляет методы для действий и вычислений над географическими точками.*/
public class RoadManager {
    public static Distance createDistance(Point start, Point finish, int length){
        return new Distance(start, finish, length);
    }

    public static Route getShortestRoute(ArrayList<Point> points){
        Algorithm algorithm = new Dijkstra();
        return algorithm.calculatePath(points);
    }
}
