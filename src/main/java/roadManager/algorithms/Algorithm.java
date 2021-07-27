package roadManager.algorithms;

import mapObjects.Node;
import roadManager.Route;

/**Общий интерфейс алгоритмов поиска путей*/
public interface Algorithm {
    Route calculatePath(Node start, Node finish);
}
