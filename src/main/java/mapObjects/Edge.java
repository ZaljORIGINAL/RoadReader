package mapObjects;

import java.util.List;

/**Класс представляет связь между двумя географическими точками (Point).*/
public class Edge {
    private final long id;
    private final List<Node> nodes;
    private final double length;
    private final double time;

    public Edge(long id, List<Node> nodes, double speed) {
        this.id = id;
        this.nodes = nodes;
        this.length = calculateLength(nodes);
        this.time = length / (speed * 1000 /60);
    }

    public long getId() {
        return id;
    }

    public long getStartNodeId() {
        return nodes.get(0).getId();
    }

    public long getFinishNodeId() {
        return nodes.get(nodes.size() - 1).getId();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public double getTime(){
        return time;
    }

    public double getLength() {
        return length;
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
