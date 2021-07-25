package RoadManager.Algorithms.Dijkstra;

import MapObjects.Distance;
import MapObjects.Point;
import RoadManager.Algorithms.Algorithm;
import RoadManager.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**Класс представляет алгоритм Дейкстры (https://ru.wikipedia.org/wiki/Алгоритм_Дейкстры)*/
public class DijkstraAlgorithms implements Algorithm {
    private HashMap<Integer, Item> constItems;
    private HashMap<Integer, Item> items;

    public DijkstraAlgorithms(){
        constItems = new HashMap<>();
        items = new HashMap<>();
    }

    @Override
    public Route calculatePath(Map<Integer, Point> points, Point start, Point finish) {
        //Потготовка
        for (Map.Entry<Integer, Point> point : points.entrySet()) {
            items.put(point.getKey(), new Item(point.getValue(), -1, point.getValue()));
        }
        Item actualPoint = new Item(start, 0, start);
        items.remove(start.getId());
        constItems.put(start.getId(), actualPoint);

        //Старт алгоритма
        while (true){
            var point = actualPoint.getPoint();
            List<Distance> distances = point.getDistances();
            for (Distance distance : distances) {
                var item = items.get(distance.getFinish().getId());
                if (item != null){
                    var newItem = new Item(distance.getFinish(), distance.getWeight() + actualPoint.getLength(), point);
                    if (item.getLength() == -1 || newItem.getLength() < item.getLength())
                        items.replace(distance.getFinish().getId(), newItem);
                }
            }

            actualPoint = getActualPoint();
            constItems.put(actualPoint.getPoint().getId(), actualPoint);
            items.remove(actualPoint.getPoint().getId());

            if(actualPoint.getPoint().equals(finish))
                return buildRoute(start, finish);
        }
    }

    private Item getActualPoint() {
        Map.Entry<Integer, Item> entry = items.entrySet().iterator().next();
        Item minItem = entry.getValue();

        for (Map.Entry<Integer, Item> itemEntry : items.entrySet()) {
            var item = itemEntry.getValue();
            if (item.getLength() < minItem.getLength() && item.getLength() != -1)
                minItem = item;
        }

        return minItem;
    }

    private Route buildRoute(Point start, Point finish){
        Stack<Point> pointStack = new Stack<>();
        Item lastItem = constItems.get(finish.getId());
        pointStack.push(lastItem.getPoint());
        while (!lastItem.getPoint().equals(start)){
            lastItem = constItems.get(lastItem.getLastPoint().getId());
            pointStack.push(lastItem.getPoint());
        }

        return new Route(pointStack, constItems.get(finish.getId()).getLength());
    }
}
