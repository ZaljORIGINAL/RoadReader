package mapObjects;

import java.util.ArrayList;
import java.util.List;

public class Way {
    private final int id;
    private final List<Integer> idNodes;
    private final byte surface;
    private int speed;
    private byte direction; //https://wiki.openstreetmap.org/wiki/RU:Key:oneway?uselang=ru

    public Way(int id, List<Integer> idNodes, byte direction){
        this.id = id;
        this.idNodes = idNodes;
        this.direction = direction;

        surface = 0;//asphalt
        speed = 60;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getNodes() {
        return idNodes;
    }

    public byte getSurface(){
        return surface;
    }

    public int getSpeed() {
        return speed;
    }

    public byte getDirection() {
        return direction;
    }
}
