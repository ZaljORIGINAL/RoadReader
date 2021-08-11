package algorithms.pointsFinders;

import mapObjects.GeographicPoint;
import mapObjects.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static algorithms.GeometryUtils.calculatePointToPointDistance;

public class QuadTree {
    protected static int pointsCount = 0;
    protected final double minLat;
    protected final double minLon;
    protected final double maxLat;
    protected final double maxLon;
    protected final double averageLat;
    protected final double averageLon;
    protected List<QuadTree> childrens;
    protected Set<Node> nodes;
    protected int level;

    public QuadTree(double minLat, double minLon, double maxLat, double maxLon, int level) {
        this.minLat = minLat;
        this.minLon = minLon;
        this.maxLat = maxLat;
        this.maxLon = maxLon;
        this.level = level;

        averageLat = (maxLat - minLat) / 2 + minLat;
        averageLon = (maxLon - minLon) / 2 + minLon;

        if (level != 0) {
            childrens = new ArrayList<>();
            nodes = null;
            childrens.add(new QuadTree(
                    averageLat, minLon,
                    maxLat, averageLon,
                    level - 1));

            childrens.add(new QuadTree(
                    averageLat, averageLon,
                    maxLat, maxLon,
                    level - 1));

            childrens.add(new QuadTree(
                    minLat, minLon,
                    averageLat, averageLon,
                    level - 1));

            childrens.add(new QuadTree(
                    minLat, averageLon,
                    averageLat, maxLon,
                    level - 1));
        } else {
            childrens = null;
            nodes = new HashSet<>();
        }

    }

    public void add(Node point) {
        if (level != 0)
            childrens.get(getChildIndex(point)).add(point);
        else {
            nodes.add(point);
            pointsCount++;
        }
    }

    /** Поиск в дереве ближайшей зарегестрированной точки.
     * @param point - географическая точка, для которой требуется найти ближайшию точку.
     * @return зарегестрированная точка, что яляется наиближайшей. */
    public Node getClosestPoint(GeographicPoint point) {
        if (level != 0)
            return childrens.get(getChildIndex(point)).getClosestPoint(point);
        else {
            double minDistance = Double.MAX_VALUE;
            Node closestNode = null;
            for (Node gPoint : nodes) {
                double distance = calculatePointToPointDistance(gPoint, point);
                if (distance == 0) {
                    return gPoint;
                } else if (distance < minDistance) {
                    minDistance = distance;
                    closestNode = gPoint;
                }
            }
            return closestNode;
        }
    }

    /** Поиск в дереве зарегестрированной точки по географическим кооринатам.
     * @param point - географическая точка, для которой требуется найти зарегестрированную точку.
     * @return зарегестрированная точка, что совпала с координатами. В случае если совпадений
     * небудет будет возвращен null */
    public Node findNode(GeographicPoint point) {
        if (level != 0)
            return childrens.get(getChildIndex(point)).findNode(point);
        else{
            for (Node gPoint : nodes) {
                if (gPoint.getLat() == point.getLat() && gPoint.getLon() == point.getLon()) {
                    return gPoint;
                }
            }

            return null;
        }
    }

    protected int getChildIndex(GeographicPoint point) {
        return (point.getLat() > averageLat ? 0 : 2) + (point.getLon() > averageLon ? 1 : 0);
    }
}
