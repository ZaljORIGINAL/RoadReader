package mapObjects;

import java.util.List;

/**Класс представляет связь между двумя географическими точками (Point).*/
public class Edge {
    private final long id;
    private final List<Long> idNodes;
    private final double speed;
    private double length;

    public Edge(long id, List<Long> idNodes, int speed, double length) {
        this.id = id;
        this.idNodes = idNodes;
        this.speed = speed;
        this.length = length;
    }

    public long getId() {
        return id;
    }

    public long getStart() {
        return idNodes.get(0);
    }

    public long getFinish() {
        return idNodes.get(idNodes.size() - 1);
    }

    public double getSpeed(){
        return speed;
    }

    public double getLength() {
        return length;
    }
}
