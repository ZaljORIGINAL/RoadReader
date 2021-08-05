package roadManager.algorithms.dijkstra;

import mapObjects.Edge;
import mapObjects.Node;
import roadManager.algorithms.Graph;
import roadManager.Route;

import java.util.*;

/**Класс представляет алгоритм Дейкстры (https://ru.wikipedia.org/wiki/Алгоритм_Дейкстры)*/
public class DijkstraAlgorithms {
    private Graph graph;

    public DijkstraAlgorithms(Graph graph) {
        this.graph = graph;
    }

    /**Вычисление маршрута в зависимости от ключевого параметра: длина, скорость, оптимальность.
     * @param weight - служит для определения ключевого параметра, относительно которого будет искаться оптимальный путь.
     * @param start - Стартовая точка.
     * @param finish - Конечная точка.*/
    public Route calculatePath(WeightСalculator weight, Node start, Node finish) {
        if (!graph.containsNode(start.getId()))
            return null;

        if (!graph.containsNode(finish.getId()))
            return null;

/*
        //Проверка: имеет ли стартовая точка исходящие пути
        if (!graph.hasOutputEdge(start.getId()))
            return null;
        //Проверка: имеет ли конечная точка входящие пути
        if (!graph.hasInputEdge(finish.getId()))
            return null;
*/

        //Старт алгоритма
        return search(weight, start, finish);
    }

    /** Алгоритм поиска*/
    private Route search(WeightСalculator weightСalculator, Node start, Node finish) {
        Map<Long, ShortestPath> shortestPathMap = new HashMap<>();
        PriorityQueue<ShortestPath> queue = new PriorityQueue<>(); //
        double minWeightToEnd = Double.MAX_VALUE;

        queue.add(new ShortestPath(start.getId(), 0, -1, null));

        ShortestPath currentPath;
        while (!queue.isEmpty()) {
            currentPath = queue.poll();

            if (currentPath.getNodeId() == finish.getId())
                return buildRoute(currentPath);
            if (currentPath.getWeight() > minWeightToEnd)
                break;

            List<Edge> edges = graph.getEdgesByNodeId(currentPath.getNodeId());

            for (Edge edge : edges) {
                double summaryWeight = weightСalculator.calculate(edge) + currentPath.getWeight();
                if (summaryWeight > minWeightToEnd)
                    continue;

                long endNodeId = edge.getEndNodeId(currentPath.getNodeId());
                ShortestPath shortestPath = shortestPathMap.get(endNodeId);
                if (shortestPath == null) {
                    shortestPath = new ShortestPath(endNodeId, summaryWeight, edge.getId(), currentPath);
                    shortestPathMap.put(endNodeId, shortestPath);
                    queue.add(shortestPath);
                } else if (shortestPath.getWeight() > summaryWeight) {
                    queue.remove(shortestPath);
                    shortestPath.setWeight(summaryWeight);
                    shortestPath.setIncomingEdgeId(edge.getId());
                    shortestPath.setShortestPath(currentPath);
                    queue.add(shortestPath);
                } else
                    continue;

                if (endNodeId == finish.getId() && summaryWeight < minWeightToEnd) {
                    minWeightToEnd = summaryWeight;
                }
            }
        }

        return null;
    }

    private Route buildRoute(ShortestPath shortestPath){
        List<Edge> edges = new ArrayList<>();
        do {
            edges.add(graph.getEdgeById(shortestPath.getIncomingEdgeId()));
            shortestPath = shortestPath.getShortestPath();
        }while (shortestPath.getIncomingEdgeId() != -1);

        Collections.reverse(edges);
        return new Route(edges, shortestPath.getNodeId());
    }
}