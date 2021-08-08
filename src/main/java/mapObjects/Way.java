package mapObjects;

import algorithms.Graph;

import java.util.*;

public class Way {
    private final long id;
    private final List<Long> idNodes;
    private final double speed;
    //private byte direction; //https://wiki.openstreetmap.org/wiki/RU:Key:oneway?uselang=ru
    private final boolean oneWay;

    public Way(long id, List<Long> idNodes, double speed, boolean oneWay){
        this.id = id;
        this.idNodes = idNodes;
        this.speed = speed;
        this.oneWay = oneWay;
    }

    public long getId() {
        return id;
    }

    public List<Long> getNodes() {
        return idNodes;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean getOneWay(){
        return oneWay;
    }

    public Set<Edge> getEdges(Map<Long, Node> nodeMap, Set<Long> towerNodesId, Set<Long> blockedNodes) {
        Set<Edge> edgeToReturn = new HashSet<>();

        //Получаем объекты точек данной дороги для последующего вычисления длины дороги
        List<Node> nodes = new ArrayList<>();
        for (Long nodeId : idNodes){
            Node node = nodeMap.get(nodeId);
            nodes.add(node);
        }

        //Счетчик для id граней
        int idEdgeOfWay = 0;
        //Индекс точки начала грани
        int indexOfStartNode = 0;
        //Проходимся по остальным
        for (int index = 1; index < nodes.size(); index++) {
            Node finishNode = nodes.get(index);
            if (towerNodesId.contains(finishNode.getId())){
                List<Node> edgeNodes = new ArrayList<>(nodes.subList(indexOfStartNode, index + 1));
                boolean isBlocked = false;
                for (Node node : edgeNodes) {
                    if (blockedNodes.contains(node.getId())){
                        isBlocked = true;
                        break;
                    }
                }

                if (!isBlocked){
                    idEdgeOfWay++;
                    edgeToReturn.add(new Edge(
                            id * 100 + idEdgeOfWay,
                            edgeNodes,
                            oneWay,
                            speed
                    ));
                }

                indexOfStartNode = index;
            }
        }

        return edgeToReturn;
    }
}
