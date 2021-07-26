package RoadManager.Algorithms;

import MapObjects.Edge;
import MapObjects.Node;
import RoadManager.Route;

import java.util.List;
import java.util.Map;

/**Общий интерфейс алгоритмов поиска путей*/
public interface Algorithm {
    Route calculatePath(Map<Node, List<Edge>> graph, Node start, Node finish);
}
