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
        Set<Integer> indexIds = new HashSet<>();

        /*Получаем индексы id тех точек дороги, что являются tower*/
        for (int indexId = 0; indexId < idNodes.size(); indexId++) {
            if (towersNodeId.contains(idNodes.get(indexId)))
                indexIds.add(indexId);
        }

        int idGraphOfWay = 0;
        for (int indexId = 0; indexId < indexIds.size() - 2; indexId++) {
            List<Long> edgeNodesId = idNodes.subList(indexId, indexId + 1);

            double length = calculateLength(
                    graph.getNodeById(edgeNodesId.get(0)),
                    graph.getNodeById(edgeNodesId.get(edgeNodesId.size()-1)));

            idGraphOfWay++;
            Edge edge = new Edge(
                    id * 100 + idGraphOfWay,
                    edgeNodesId,
                    speed,
                    length
            );
        }

        return new HashSet<>();
    }

    private double calculateLength(Node start, Node finish){
/*        double sinD = Math.sin(start.getLat()) * Math.sin(finish.getLat()) +
                Math.cos(start.getLat()) * Math.cos(finish.getLat()) * Math.cos(start.getLon() - finish.getLon());

        */

        return 0;
    }
}
