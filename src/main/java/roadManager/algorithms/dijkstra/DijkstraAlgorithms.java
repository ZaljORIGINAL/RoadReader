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
    private Map<Integer, ShortestPath> shortestPathMap;
    /*http://proglang.su/java/treeset-class*/
    private TreeSet<ShortestPath> potentialPaths;

    public DijkstraAlgorithms(Graph graph){
        this.graph = graph;
        shortestPathMap = new HashMap<>();
        potentialPaths = new TreeSet<>();
    }

    // https://www.baeldung.com/java-graphs
    /*TODO Требуется работа с графом. Предоставлять пути и точки должен граф.
    *  вызывать метод getEdges() из объекта графа. Проверить наличие пути К графу
    *  и как такого присутствия в переданных точках. Так же сделать проверку на
    *  получаемую конечную точку из пути: не является ли она стартовой.*/
    @Override
    public Route calculatePath(Node start, Node finish) {
        if (!graph.containsNode(start))
            return null;

        if (!graph.containsNode(finish))
            return null;

        //Проверка: имеет ли стартовая точка исходящие пути
        if (!graph.hasOutputEdge(start))
            return null;
        //Проверка: имеет ли конечная точка входящие пути
        if (!graph.hasInputEdge(finish))
            return null;

        //Старт алгоритма
        return search(start, finish);
    }

    private Route search(Node start, Node finish){
        /*ShortestPath actualPath = new ShortestPath(start, 0, null);
        shortestPathMap.put(start.getId() ,actualPath);
        while (true){
            Node node = actualPath.getNode();
            List<Edge> edges = graph.getEdgesByNode(node);
            for (Edge edge : edges) {
                //Проверка на исходящий путь
                if (!edge.getFinish().equals(node)){
                    ShortestPath path = new ShortestPath(edge.getFinish(), edge.getLength() + actualPath.getWeight(), edge);
                    *//*TODO Если ранее ранее рассматривался путь к данной точке получить значение, если нет то добавить.
                    *  Если нынешний маршрут лучше заменить его.*//*
                    if (!potentialPaths.isEmpty()){
                        ShortestPath alternativePath = getAlternativePath(path);
                        if (alternativePath != null){
                            if (path.getWeight() < alternativePath.getWeight()){
                                potentialPaths.remove(alternativePath);
                                potentialPaths.add(path);
                            }
                        }else
                            potentialPaths.add(path);
                    }else
                        //Добавление первого предполагаемого пути
                        potentialPaths.add(path);
                }
            }

            actualPath = potentialPaths.first();
            potentialPaths.remove(actualPath);
            shortestPathMap.put(actualPath.getNode().getId(), actualPath);

            if(actualPath.getNode().equals(finish))
                return buildRoute(start, finish);
        }*/
        return null;
    }

    private Route buildRoute(Node start, Node finish){
/*        Stack<Edge> nodeStack = new Stack<>();
        ShortestPath shortestPath = shortestPathMap.get(finish.getId());
        Edge edge = shortestPath.getIncomingEdge();
        nodeStack.push(edge);
        while (!edge.getStart().equals(start)){
            shortestPath = shortestPathMap.get(edge.getStart().getId());
            edge = shortestPath.getIncomingEdge();
            nodeStack.push(edge);
        }

        return new Route(nodeStack, shortestPathMap.get(finish.getId()).getWeight());*/
        return null;
    }

    private ShortestPath getAlternativePath(ShortestPath path){
        Iterator<ShortestPath> iterator = potentialPaths.iterator();
        while (iterator.hasNext()) {
            ShortestPath potentialPath = iterator.next();
            if (potentialPath.getNode().getId() == path.getNode().getId()) {
                return potentialPath;
            }
        }

        return shortestPathMap.get(path.getNode().getId());
    }
}
