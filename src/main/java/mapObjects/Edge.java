package mapObjects;

import java.util.List;

/**Класс представляет связь между двумя географическими точками (Point).*/
public class Edge {
    private final long id;
    private final List<Node> nodes;
    private final boolean oneWay;
    private final double length;
    private final double speed; //set speed

    public Edge(long id, List<Node> nodes, boolean oneWay, double speed) {
        this.id = id;
        this.nodes = nodes;
        this.oneWay = oneWay;
        this.length = calculateLength(nodes);
        this.speed = speed;
    }

    public long getId() {
        return id;
    }

    public long getFirstNodeId() {
        return nodes.get(0).getId();
    }

    public long getLastNodeId() {
        return nodes.get(nodes.size() - 1).getId();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public boolean isOneWay() {
        return oneWay;
    }

    public double getLength() {
        return length;
    }

    public double getSpeed(){
        return speed;
    }

    public double getTime() {
        return length / (speed * 1000 / 60);
    }

    public long getEndNodeId(long nodeId){
        Node node = nodes.get(0);
        if (node.getId() == nodeId)
            return nodes.get(nodes.size() - 1).getId();
        else
            return node.getId();
    }

    public static double calculateLength(List<Node> nodes){
        double length = 0;
        for (int index = 0; index < nodes.size() - 1; index++){
            double distance = calculatePointToPointDistance(
                    nodes.get(index),
                    nodes.get(index + 1));

            length+= distance;
        }

        return length;
    }

    public static double calculatePointToPointDistance(Node node1, Node node2){
        double R = 6378137;
        double C = Math.PI/180;

        double X1 = Math.cos(C * node1.getLat()) * Math.cos(C * node1.getLon());
        double X2 = Math.cos(C * node2.getLat()) * Math.cos(C * node2.getLon());

        double Y1 = Math.cos(C * node1.getLat()) * Math.sin(C * node1.getLon());
        double Y2 = Math.cos(C * node2.getLat()) * Math.sin(C * node2.getLon());

        double Z1 = Math.sin(C * node1.getLat());
        double Z2 = Math.sin(C * node2.getLat());

        return R * Math.acos(X1 * X2 + Y1 * Y2 + Z1 * Z2);
    }
}
