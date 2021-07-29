package mapObjects;

import roadManager.algorithms.Graph;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Way {
    private final int id;
    private final List<Long> idNodes;
    private int wayType;
    private double speed;
    //private byte direction; //https://wiki.openstreetmap.org/wiki/RU:Key:oneway?uselang=ru

    public Way(int id, List<Long> idNodes, int wayType, int speed){
        this.id = id;
        this.idNodes = idNodes;
        this.wayType = wayType;
        this.speed = speed;
    }

    public int getId() {
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

    public Set<Edge> getEdges(Graph graph, Set<Long> towerNodes) {
        //TODO Реализовать.
        return new HashSet<>();
    }
}
