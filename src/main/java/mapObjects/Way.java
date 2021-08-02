package mapObjects;

import roadManager.algorithms.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class Way {
    private final long id;
    private final List<Long> idNodes;
    private int wayType;
    private double speed;
    //private byte direction; //https://wiki.openstreetmap.org/wiki/RU:Key:oneway?uselang=ru

    public Way(long id, List<Long> idNodes, int wayType, int speed){
        this.id = id;
        this.idNodes = idNodes;
        this.wayType = wayType;
        this.speed = speed;
    }

    public long getId() {
        return id;
    }

    public List<Long> getNodes() {
        return idNodes;
    }

    public int getWayType(){
        return wayType;
    }

    public double getSpeed() {
        return speed;
    }

    public Set<Edge> getEdges(Graph graph, Set<Long> towersNodeId) {
        Set<Edge> edgeToReturn = new HashSet<>();

        //Получаем объекты точек данной дороги для последующего вычисления длины дороги
        List<Node> nodes = new ArrayList<>();
        for (Long nodeId : idNodes){
            Node node = graph.getNodeById(nodeId);
            nodes.add(node);
        }

        //Счетчик для id граней
        int idEdgeOfWay = 0;
        //Индекс точки начала грани
        int indexOfStartNode = 0;
        //Проходимся по остальным
        for (int index = 1; index < nodes.size(); index++) {
            Node finishNode = nodes.get(index);
            if (towersNodeId.contains(finishNode.getId())){
                List<Node> edgeNodes = nodes.subList(indexOfStartNode, index + 1);

                idEdgeOfWay++;
                edgeToReturn.add(new Edge(
                        id * 100 + idEdgeOfWay,
                        new ArrayList<>(edgeNodes),
                        speed
                ));

                //В случае свободного движения
                Collections.reverse(edgeNodes);
                idEdgeOfWay++;
                edgeToReturn.add(new Edge(
                        id * 100 + idEdgeOfWay,
                        new ArrayList<>(edgeNodes),
                        speed
                ));
                indexOfStartNode = index;
            }

        }

        return edgeToReturn;
    }
}
