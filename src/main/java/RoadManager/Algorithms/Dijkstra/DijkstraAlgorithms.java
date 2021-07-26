package RoadManager.Algorithms.Dijkstra;

import MapObjects.Edge;
import MapObjects.Node;
import RoadManager.Algorithms.Algorithm;
import RoadManager.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**Класс представляет алгоритм Дейкстры (https://ru.wikipedia.org/wiki/Алгоритм_Дейкстры)*/
public class DijkstraAlgorithms implements Algorithm {
    private HashMap<Integer, Item> visitedNodes;
    private HashMap<Integer, Item> nodes;

    public DijkstraAlgorithms(){
        visitedNodes = new HashMap<>();
        nodes = new HashMap<>();
    }

    // https://www.baeldung.com/java-graphs
    /*TODO Требуется работа с графом. Предоставлять пути и точки должен граф.
    *  вызывать метод getEdges() из объекта графа. Проверить наличие пути К графу
    *  и как такого присутствия в переданных точках. Так же сделать проверку на
    *  получаемую конечную точку из пути: не является ли она стартовой.*/
    @Override
    public Route calculatePath(Map<Integer, Node> points, Node start, Node finish) {
        //Потготовка
        for (Map.Entry<Integer, Node> point : points.entrySet()) {
            nodes.put(point.getKey(), new Item(point.getValue(), -1, point.getValue()));
        }
        Item actualPoint = new Item(start, 0, start);
        nodes.remove(start.getId());
        visitedNodes.put(start.getId(), actualPoint);

        //Старт алгоритма
        while (true){
            var point = actualPoint.getPoint();
            List<Edge> edges = point.getEdges();
            for (Edge edge : edges) {
                var item = nodes.get(edge.getFinish().getId());
                if (item != null){
                    var newItem = new Item(edge.getFinish(), edge.getLength() + actualPoint.getLength(), point);
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
    private Item getActualPoint() {
        Map.Entry<Integer, Item> entry = nodes.entrySet().iterator().next();
        Item minItem = entry.getValue();

        for (Map.Entry<Integer, Item> itemEntry : nodes.entrySet()) {
            var item = itemEntry.getValue();
            if (item.getLength() < minItem.getLength() && item.getLength() != -1)
                minItem = item;
        }

        return minItem;
    }

    private Route buildRoute(Node start, Node finish){
        Stack<Node> nodeStack = new Stack<>();
        Item lastItem = visitedNodes.get(finish.getId());
        nodeStack.push(lastItem.getPoint());
        while (!lastItem.getPoint().equals(start)){
            lastItem = visitedNodes.get(lastItem.getLastPoint().getId());
            nodeStack.push(lastItem.getPoint());
        }

        return new Route(nodeStack, visitedNodes.get(finish.getId()).getLength());
    }
}
