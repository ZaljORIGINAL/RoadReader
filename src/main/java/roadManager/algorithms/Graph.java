package roadManager.algorithms;

import mapObjects.Edge;
import mapObjects.Node;
import mapObjects.Way;
import roadManager.RoadManager;

import java.util.*;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Graph {
    private Map<Long, Node> nodeMap;
    private Map<Long, Edge> edgeMap;
    private Map<Long, List<Long>> relations;


    public Graph(Map<Long, Node> allNodes, Set<Long> towerNodesId, Map<Long, Way> ways){
        nodeMap = allNodes;
        Collection<Way> waysCollection = ways.values();
        edgeMap = waysCollection.stream().flatMap(way -> way.getEdges(this, towerNodesId).stream())
                .collect(Collectors.toMap(Edge::getId, edge -> edge));
        relations = getRelations(towerNodesId, edgeMap);
    }

    private Map<Long, List<Long>> getRelations(Set<Long> towerNodesId, Map<Long, Edge> edgeMap) {
        Map<Long, List<Long>> relations = new HashMap<>();

        for (Long towerId : towerNodesId) {
            List<Long> dependentEdges = new ArrayList<>();
            for (Map.Entry<Long, Edge> entry : edgeMap.entrySet()) {
                Edge edge = entry.getValue();
                if (edge.getStart() == towerId || edge.getFinish() == towerId)
                    dependentEdges.add(edge.getId());
            }

            relations.put(towerId, dependentEdges);
        }

        return relations;
    }

    public Node getNodeById(long nodeId) {
        return nodeMap.get(nodeId);
    }

    public List<Edge> getEdgesByNodeId(long nodeId) {
        List<Long> edgesId = relations.get(nodeId);

        return edgesId.stream()
                .map(edgeId -> edgeMap.get(edgeId))
                .collect(Collectors.toList());
    }

    /**Метод ответить содержит ли граф узел.*/
    public boolean containsNode(long nodeId){
        return nodeMap.containsKey(nodeId);
    }

    /**Метод ответить есть ли у точки входящие пути.*/
    public boolean hasInputEdge(Node node){
        // TODO Реализовать.

        return false;
    }

    /**Метод ответить есть ли у точки исходящие пути.*/
    public boolean hasOutputEdge(Node node){
        // TODO Реализовать.

        return false;
    }
}
