package roadManager.algorithms;

import mapObjects.Edge;
import mapObjects.Node;
import mapObjects.Way;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private static final Logger logger = LoggerFactory.getLogger(Graph.class);

    private Map<Long, Node> nodeMap;
    private Map<Long, Edge> edgeMap;
    private Map<Long, List<Long>> relations;


    public Graph(Map<Long, Node> allNodes, Set<Long> towerNodesId, Map<Long, Way> ways){
        nodeMap = allNodes;
        Collection<Way> waysCollection = ways.values();
        edgeMap = waysCollection.stream().flatMap(way -> way.getEdges(this, towerNodesId).stream())
                .collect(Collectors.toMap(Edge::getId, edge -> edge));
        logger.info("Количество граней: " + edgeMap.size());
        relations = buildRelations(towerNodesId, edgeMap);
        logger.info("Зависимости выстроены: " + relations.size());
    }

    public Map<Long, Node> getAllNodes(){
        return nodeMap;
    }

    public Map<Long, Edge> getAllEdges(){
        return edgeMap;
    }

    public Node getNodeById(long nodeId) {
        return nodeMap.get(nodeId);
    }

    public Edge getEdgeById(long edgeId) {return edgeMap.get(edgeId);}

    /**Вернет входящие и исходящие грани.*/
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
    public boolean hasInputEdge(long nodeId){
        Collection<Edge> edges = edgeMap.values();

        return edges.stream()
                .anyMatch(edge -> edge.getLastNodeId() == nodeId);
    }

    /**Метод ответить есть ли у точки исходящие пути.*/
    public boolean hasOutputEdge(long nodeId){
        Collection<Edge> edges = edgeMap.values();

        return edges.stream()
                .anyMatch(edge -> edge.getFirstNodeId() == nodeId);
    }

    private Map<Long, List<Long>> buildRelations(Set<Long> towerNodesId, Map<Long, Edge> edgeMap) {
        Map<Long, List<Long>> relations = new HashMap<>();

        for (Long towerId : towerNodesId) {
            List<Long> dependentEdges = new ArrayList<>();
            for (Map.Entry<Long, Edge> entry : edgeMap.entrySet()) {
                Edge edge = entry.getValue();
                if (edge.getFirstNodeId() == towerId)
                    dependentEdges.add(edge.getId());
                else if (edge.getLastNodeId() == towerId && !edge.isOneWay())
                    dependentEdges.add(edge.getId());
            }

            relations.put(towerId, dependentEdges);
        }

        return relations;
    }

}
