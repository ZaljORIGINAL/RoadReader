package roadManager.algorithms.dijkstra;

import mapObjects.Edge;
import mapObjects.Node;
import roadManager.algorithms.Algorithm;
import roadManager.algorithms.Graph;
import roadManager.Route;

import java.util.*;

/**Класс представляет алгоритм Дейкстры (https://ru.wikipedia.org/wiki/Алгоритм_Дейкстры)*/
public class DijkstraAlgorithms implements Algorithm {
    private Graph graph;
    private Map<Long, ShortestPath> shortestPathMap;
    /*http://proglang.su/java/treeset-class*/
    private TreeSet<ShortestPath> potentialPaths;

    public DijkstraAlgorithms(Graph graph) {
        this.graph = graph;
        shortestPathMap = new HashMap<>();
        potentialPaths = new TreeSet<>();
    }

    // https://www.baeldung.com/java-graphs
    @Override
    public Route calculatePath(Node start, Node finish) {
        if (!graph.containsNode(start.getId()))
            return null;

        if (!graph.containsNode(finish.getId()))
            return null;

        //Проверка: имеет ли стартовая точка исходящие пути
        if (!graph.hasOutputEdge(start.getId()))
            return null;
        //Проверка: имеет ли конечная точка входящие пути
        if (!graph.hasInputEdge(finish.getId()))
            return null;

        //Старт алгоритма
        return search(start, finish);
    }

    private Route search(Node start, Node finish) {
        ShortestPath actualPath = new ShortestPath(start.getId(), 0, -1);
        shortestPathMap.put(start.getId(), actualPath);

        //Пока точка от которой будут рассматриваться следующие пути не равна конечной
        while (actualPath.getNodeId() != finish.getId()) {
            long nodeId = actualPath.getNodeId();
            List<Edge> edges = graph.getEdgesByNodeId(nodeId);
            for (Edge edge : edges) {
                if (edge.getStartNodeId() == nodeId && edge.getFinishNodeId() != nodeId){
                    ShortestPath path = new ShortestPath(edge.getFinishNodeId(), edge.getLength() + actualPath.getWeight(), edge.getId());
                    if (!potentialPaths.isEmpty()) {
                        ShortestPath alternativePath = getAlternativePath(path);
                        if (alternativePath != null) {
                            if (path.getWeight() < alternativePath.getWeight()) {
                                potentialPaths.remove(alternativePath);
                                potentialPaths.add(path);
                            }
                        } else
                            potentialPaths.add(path);
                    } else
                        //Добавление первого предполагаемого пути
                        potentialPaths.add(path);
                }
            }

            actualPath = potentialPaths.first();
            potentialPaths.remove(actualPath);
            shortestPathMap.put(actualPath.getNodeId(), actualPath);
        }

        return buildRoute(start, finish);
    }

    private Route buildRoute(Node start, Node finish){
        List<Edge> edgesList = new ArrayList<>();
        ShortestPath shortestPath = shortestPathMap.get(finish.getId());
        long edgeId = shortestPath.getIncomingEdgeId();
        Edge edge = graph.getEdgeById(edgeId);
        edgesList.add(edge);
        while (edge.getStartNodeId() != start.getId()){
            shortestPath = shortestPathMap.get(edge.getStartNodeId());
            edgeId = shortestPath.getIncomingEdgeId();
            edge = graph.getEdgeById(edgeId);
            edgesList.add(edge);
        }

        Collections.reverse(edgesList);

        return new Route(edgesList);
    }

    private ShortestPath getAlternativePath(ShortestPath path){
        Iterator<ShortestPath> iterator = potentialPaths.iterator();
        while (iterator.hasNext()) {
            ShortestPath potentialPath = iterator.next();
            if (potentialPath.getNodeId() == path.getNodeId()) {
                return potentialPath;
            }
        }

        return shortestPathMap.get(path.getNodeId());
    }
}