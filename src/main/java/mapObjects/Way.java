package mapObjects;

import java.util.ArrayList;
import java.util.List;

public class Way {
    private final int id;
    private List<Integer> idNodes;
    private byte wayDirection; //https://wiki.openstreetmap.org/wiki/RU:Key:oneway?uselang=ru

    public Way(int id, List<Integer> idNodes, byte wayDirection){
        this.id = id;
        this.idNodes = idNodes;
        this.wayDirection = wayDirection;
    }

    public Way(int id, byte wayDirection){
        this.id = id;
        this.wayDirection = wayDirection;

        idNodes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Integer> getNodes() {
        return idNodes;
    }

    public byte getWayDirection() {
        return wayDirection;
    }

    public void addNode(int nodeId) {
        idNodes.add(nodeId);
    }

    public void removeNode(int nodeId) {
        idNodes.remove(nodeId);
    }
}
