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

    private final Map<Long, Node> nodeMap;
    private final Map<Long, Edge> edgeMap;
    private final Map<Long, List<Long>> relations = new HashMap<>();

    public Graph(Set<Node> allNodes, Set<Long> towerNodesId, Set<Way> ways){
        nodeMap = allNodes.stream()
                .collect(Collectors.toMap(Node::getId, node -> node));
        edgeMap = ways.stream()
                .flatMap(way -> way.getEdges(this, towerNodesId).stream())
                .collect(Collectors.toMap(Edge::getId, edge -> edge));
        logger.info("Количество граней: " + edgeMap.size());
        fillRelations(edgeMap.values());
        logger.info("Зависимости выстроены: " + relations.size());
    }

    public Map<Long, Node> getAllNodes(){
        return nodeMap;
    }

    public Map<Long, Edge> getAllEdges(){
        return edgeMap;
    }

    public Set<Node> getTowerNodes(){
        return relations.keySet().stream()
                .map(nodeMap::get)
                .collect(Collectors.toSet());
    }

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

/*    *//**Метод ответить есть ли у точки входящие пути.*//*
    public boolean hasInputEdge(long nodeId){
        Collection<Edge> edges = edgeMap.values();

        return edges.stream()
                .anyMatch(edge -> edge.getLastNodeId() == nodeId);
    }

    *//**Метод ответить есть ли у точки исходящие пути.*//*
    public boolean hasOutputEdge(long nodeId){
        Collection<Edge> edges = edgeMap.values();

        return edges.stream()
                .anyMatch(edge -> edge.getFirstNodeId() == nodeId);
    }*/

    private void fillRelations(Collection<Edge> edges) {
        for (Edge edge : edges) {
            long node = edge.getFirstNodeId();
            List<Long> nodeEdges = relations.getOrDefault(node, new ArrayList<>());
            nodeEdges.add(edge.getId());
            relations.put(node, nodeEdges);

            node = edge.getLastNodeId();
            nodeEdges = relations.getOrDefault(node, new ArrayList<>());
            if (!edge.isOneWay()){
                nodeEdges.add(edge.getId());
            }
            relations.put(node, nodeEdges);
        }
    }
}
