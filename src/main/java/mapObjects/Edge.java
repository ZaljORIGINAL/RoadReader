package mapObjects;

import roadManager.algorithms.GeographicPoint;

import java.util.List;

import static roadManager.algorithms.GeometryUtils.calculatePointToPointDistance;

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

    public double getSpeed() {
        return speed;
    }

    public double getTime() {
        return length / (speed * 1000 / 60);
    }

    public long getEndNodeId(long nodeId) {
        Node node = nodes.get(0);
        if (node.getId() == nodeId)
            return nodes.get(nodes.size() - 1).getId();
        else
            return node.getId();
    }

    public static double calculateLength(List<Node> nodes) {
        double length = 0;
        for (int index = 0; index < nodes.size() - 1; index++) {
            double distance = calculatePointToPointDistance(
                    nodes.get(index),
                    nodes.get(index + 1));

            length += distance;
        }

        return length;
    }
}
