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


    public Graph(Set<Node> allNodes, Set<Long> towerNodes, Set<Way> ways){
        nodeMap = allNodes.stream()
                .collect(Collectors.toMap(Node::getId, n -> n));
        edgeMap = ways.stream().flatMap(way -> way.getEdges(this, towerNodes).stream())
                .collect(Collectors.toMap(Edge::getId, edge -> edge));

    }

    public Graph(List<Node> relations, String[] edgesDescriptions){
//         this.relations = RoadManager.generateGraph(relations, edgesDescriptions);
    }

    public Map<Long, List<Long>> getRelations(){
        return relations;
    }

    public List<Edge> getEdgesByNode(Node node){
        long id = node.getId();
        /* TODO Попробовать через streamAPI выдать требуемые Edge*/
        return null;
    }

    /**Метод ответить содержит ли граф узел.*/
    public boolean containsNode(Node node){
        return relations.containsKey(node);
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
