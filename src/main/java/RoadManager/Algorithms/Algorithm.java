package RoadManager.Algorithms;

import MapObjects.Node;
import RoadManager.Route;

import java.util.Map;

/**Общий интерфейс алгоритмов поиска путей*/
public interface Algorithm {
    Route calculatePath(Map<Integer, Node> points, Node start, Node finish);
}
