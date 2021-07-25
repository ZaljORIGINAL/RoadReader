package RoadManager.Algorithms;

import MapObjects.Point;
import RoadManager.Route;

import java.util.List;

/**Общий интерфейс алгоритмов поиска путей*/
public interface Algorithm {
    Route calculatePath(List<Point> points);
}
