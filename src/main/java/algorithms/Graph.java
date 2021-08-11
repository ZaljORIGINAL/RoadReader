package algorithms;

import mapObjects.Edge;
import mapObjects.Node;
import mapObjects.Way;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private static final Logger logger = LoggerFactory.getLogger(Graph.class);

    private final Map<Long, Node> nodeMap;
    private final Map<Long, Edge> edgeMap;
    private final Map<Long, List<Long>> relations = new HashMap<>();

    public Graph(Set<Node> allNodes, Set<Long> towerNodesId, Set<Way> ways){
        nodeMap = allNodes.stream()
                .collect(Collectors.toMap(Node::getId, node -> node));
        edgeMap = ways.stream()
                .flatMap(way -> way.getEdges(nodeMap, towerNodesId).stream())
                .collect(Collectors.toMap(Edge::getId, edge -> edge));
        logger.info("Количество граней: " + edgeMap.size());
        fillRelations(edgeMap.values());
        logger.info("Зависимости выстроены: " + relations.size());
    }

    public Set<Node> getNodes() {
        return new HashSet<>(nodeMap.values());
    }

    public Set<Node> getTowerNodes() {return relations.keySet().stream()
            .map(nodeMap::get)
            .collect(Collectors.toSet());}

    public Node getNodeById(long nodeId) {
        return nodeMap.get(nodeId);
    }

    public Edge getEdgeById(long edgeId) {return edgeMap.get(edgeId);}

    /**Выдаст исходящие грани из tower точки.*/
    public List<Edge> getEdgesByNodeId(long nodeId) {
        return relations.get(nodeId).stream()
                .map(edgeMap::get)
                .collect(Collectors.toList());
    }

    /**Метод ответить содержит ли граф узел.*/
    public boolean containsNode(long nodeId){
        return nodeMap.containsKey(nodeId);
    }

    private void fillRelations(Collection<Edge> edges) {
        long nodeId;
        List<Long> nodeEdges;

        for (Edge edge : edges) {
            nodeId = edge.getFirstNodeId();
            nodeEdges = relations.getOrDefault(nodeId, new ArrayList<>());
            nodeEdges.add(edge.getId());
            relations.put(nodeId, nodeEdges);

            nodeId = edge.getLastNodeId();
            nodeEdges = relations.getOrDefault(nodeId, new ArrayList<>());
            if (!edge.isOneWay()){
                nodeEdges.add(edge.getId());
            }
            relations.put(nodeId, nodeEdges);
        }
    }
}
