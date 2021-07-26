package RoadManager.Algorithms.Dijkstra;

import MapObjects.Edge;
import MapObjects.Node;
import RoadManager.Algorithms.Algorithm;
import RoadManager.Route;

import java.util.*;

/**Класс представляет алгоритм Дейкстры (https://ru.wikipedia.org/wiki/Алгоритм_Дейкстры)*/
public class DijkstraAlgorithms implements Algorithm {
    private List<TotalPath> visitedNodes;
    private TreeSet<TotalPath> nodes;

    public DijkstraAlgorithms(){
        visitedNodes = new ArrayList<>();
        nodes = new TreeSet<>();
    }

    // https://www.baeldung.com/java-graphs
    /*TODO Требуется работа с графом. Предоставлять пути и точки должен граф.
    *  вызывать метод getEdges() из объекта графа. Проверить наличие пути К графу
    *  и как такого присутствия в переданных точках. Так же сделать проверку на
    *  получаемую конечную точку из пути: не является ли она стартовой.*/
    @Override
    public Route calculatePath(Map<Node, List<Edge>> graph, Node start, Node finish) {


        //Потготовка
        for (Map.Entry<Node, List<Edge>> entry : graph.entrySet()) {
            nodes.add(new TotalPath(entry.getKey(), -1, entry.getKey()));
        }
        TotalPath actualPoint = new TotalPath(start, 0, start);
        nodes.remove(start.getId());
        visitedNodes.put(start.getId(), actualPoint);

        //Старт алгоритма
        while (true){
            var point = actualPoint.getPoint();
            List<Edge> edges = point.getEdges();
            for (Edge edge : edges) {
                var item = nodes.get(edge.getFinish().getId());
                if (item != null){
                    var newItem = new TotalPath(edge.getFinish(), edge.getLength() + actualPoint.getLength(), point);
                    if (item.getLength() == -1 || newItem.getLength() < item.getLength())
                        nodes.replace(edge.getFinish().getId(), newItem);
                }
            }

            actualPoint = getActualPoint();
            visitedNodes.put(actualPoint.getPoint().getId(), actualPoint);
            nodes.remove(actualPoint.getPoint().getId());

            if(actualPoint.getPoint().equals(finish))
                return buildRoute(start, finish);
        }
    }

    /*TODO Необходимость в методе пропадет с применением TreeSet. Требуется что
    *  бы класс Item наследовал интерфейс Comparable<Person>*/
    private TotalPath getActualPoint() {
        Map.Entry<Integer, TotalPath> entry = nodes.entrySet().iterator().next();
        TotalPath minTotalPath = entry.getValue();

        for (Map.Entry<Integer, TotalPath> itemEntry : nodes.entrySet()) {
            var item = itemEntry.getValue();
            if (item.getLength() < minTotalPath.getLength() && item.getLength() != -1)
                minTotalPath = item;
        }

        return minTotalPath;
    }

    private Route buildRoute(Node start, Node finish){
        Stack<Node> nodeStack = new Stack<>();
        TotalPath lastTotalPath = visitedNodes.get(finish.getId());
        nodeStack.push(lastTotalPath.getPoint());
        while (!lastTotalPath.getPoint().equals(start)){
            lastTotalPath = visitedNodes.get(lastTotalPath.getLastPoint().getId());
            nodeStack.push(lastTotalPath.getPoint());
        }

        return new Route(nodeStack, visitedNodes.get(finish.getId()).getLength());
    }
}
