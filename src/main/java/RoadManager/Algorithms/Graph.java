package RoadManager.Algorithms;

import MapObjects.Edge;
import MapObjects.Node;
import RoadManager.RoadManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Node, List<Edge>> nodes;

    public Graph(List<Node> nodes, String[] edgesDescriptions){
         this.nodes = RoadManager.generateGraph(nodes, edgesDescriptions);
    }

    public List<Node> getNodes(){
        var keys = nodes.keySet();
        List<Node> nodes = new ArrayList<>();
        for (Node node : keys) {
            nodes.add(node);
        }

        return nodes;
    }

    public List<Edge> getEdgesByNode(Node node){
        return nodes.get(node);
    }

    /**Метод ответить содержит ли граф узел.*/
    public boolean containsNode(Node node){
        return nodes.containsKey(node);
    }

    /**Метод ответить есть ли у точки входящие пути.*/
    public boolean hasInputEdge(Node node){
        List<Edge> edges = nodes.get(node);
        for (Edge edge : edges){
            if (edge.getFinish().equals(node))
                return true;
        }

        return false;
    }

    /**Метод ответить есть ли у точки исходящие пути.*/
    public boolean hasOutputEdge(Node node){
        List<Edge> edges = nodes.get(node);
        for (Edge edge : edges){
            if (!edge.getFinish().equals(node))
                return true;
        }

        return false;
    }
}
