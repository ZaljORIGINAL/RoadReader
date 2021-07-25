package RoadManager.Algorithms;

import MapObjects.Point;
import RoadManager.Route;

import java.util.List;
import java.util.Map;

/**Общий интерфейс алгоритмов поиска путей*/
public interface Algorithm {
    Route calculatePath(Map<Integer, Point> points, Point start, Point finish);
}
