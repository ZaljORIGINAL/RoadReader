package mapObjects;

import java.util.List;

public class Way {
    private final int id;
    private final List<Long> idNodes;
    private int wayType;
    private int maxSpeed;
    //private byte direction; //https://wiki.openstreetmap.org/wiki/RU:Key:oneway?uselang=ru

    public Way(int id, List<Long> idNodes, int wayType, int maxSpeed){
        this.id = id;
        this.idNodes = idNodes;
        this.wayType = wayType;
        this.maxSpeed = maxSpeed;
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

    public int getMaxSpeed() {
        return maxSpeed;
    }
}
