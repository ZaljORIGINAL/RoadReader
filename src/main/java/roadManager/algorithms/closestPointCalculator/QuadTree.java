package roadManager.algorithms.closestPointCalculator;

import mapObjects.Node;
import roadManager.algorithms.GeographicPoint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static roadManager.algorithms.GeometryUtils.calculatePointToPointDistance;

public class QuadTree {
    private final double minLat;
    private final double minLon;
    private final double maxLat;
    private final double maxLon;
    private final double averageLat;
    private final double averageLon;
    private final List<QuadTree> childrens;
    private final Set<Node> nodes;
    private final int level;

    public QuadTree(double minLat, double minLon, double maxLat, double maxLon, int level) {
        this.minLat = minLat;
        this.minLon = minLon;
        this.maxLat = maxLat;
        this.maxLon = maxLon;
        this.level = level;

        averageLat = (maxLat - minLat) / 2 + minLat;
        averageLon = (maxLon - minLon) / 2 + minLon;

        if (level != 0) {
            //Ходим по часовой, начинаем с левого нижнего, заканчиваем правым нижним.
            childrens = new ArrayList<>();
            nodes = null;
            childrens.add(new QuadTree(
                    averageLat, minLon,
                    maxLat, averageLon,
                    level -1));

            childrens.add(new QuadTree(
                    averageLat, averageLon,
                    maxLat, maxLon,
                    level - 1));

            childrens.add(new QuadTree(
                    minLat, minLon,
                    averageLat, averageLon,
                    level -1));

            childrens.add(new QuadTree(
                    minLat, averageLon,
                    averageLat, maxLon,
                    level - 1));
        } else {
            childrens = null;
            nodes = new HashSet<>();
        }
    }

    public void add(Node node) {
        if (level != 0)
            childrens.get(getChildIndex(node)).add(node);
        else
            nodes.add(node);
    }

    public Node findClosest(GeographicPoint point) {
        if (level != 0)
            return childrens.get(getChildIndex(point)).findClosest(point);
        else{
            double minDistance = Double.MAX_VALUE;
            Node closestNode = null;
            for (Node node : nodes) {
                double distance = calculatePointToPointDistance(node, point);
                if (distance == 0) {
                    return node;
                } else if (distance < minDistance) {
                    minDistance = distance;
                    closestNode = node;
                }
            }
            return closestNode;
        }
    }

    private int getChildIndex(GeographicPoint point) {
        return (point.getLat() > averageLat ? 0 : 2) + (point.getLon() > averageLon ? 1 : 0);
    }
}
