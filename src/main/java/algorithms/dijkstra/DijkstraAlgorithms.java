package algorithms.dijkstra;

import mapObjects.Edge;
import mapObjects.Node;
import algorithms.Graph;
import mapObjects.Route;
import mapObjects.parseConfigurations.BlockedPointConfiguration;

import java.util.*;

/**Класс представляет алгоритм Дейкстры (https://ru.wikipedia.org/wiki/Алгоритм_Дейкстры)*/
public class DijkstraAlgorithms {
    private Graph graph;
    private BlockedPointConfiguration blockedPoint;

    public DijkstraAlgorithms(Graph graph) {
        this.graph = graph;
        blockedPoint = new BlockedPointConfiguration();
    }

    public void setBlockedPointConfiguration(BlockedPointConfiguration configuration) {
        this.blockedPoint = configuration;
    }

    /**Вычисление маршрута в зависимости от ключевого параметра: длина, скорость, оптимальность.
     * @param weight - служит для определения ключевого параметра, относительно которого будет искаться оптимальный путь.
     * @param start - Стартовая точка.
     * @param finish - Конечная точка.*/
    public Route calculateRoute(WeightСalculator weight, Node start, Node finish) {
        if (!graph.containsNode(start.getId()))
            return null;

        if (!graph.containsNode(finish.getId()))
            return null;

        //Старт алгоритма
        return search(weight, start, finish);
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
                //Проверка заблокирована ли грань
                boolean isBlocked = false;
                List<Node> nodes = edge.getNodes();
                for (Node node : nodes) {
                    if (blockedPoint.isBlocked(node.getId())) {
                        isBlocked = true;
                        break;
                    }
                }

                if (isBlocked)
                    continue;

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
}