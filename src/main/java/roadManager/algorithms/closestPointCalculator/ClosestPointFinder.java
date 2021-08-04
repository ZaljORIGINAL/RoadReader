package roadManager.algorithms.closestPointCalculator;

import mapObjects.Node;
import roadManager.algorithms.GeographicPoint;
import roadManager.algorithms.GeometryUtils;

/**Класс ищущий ближайшую точку графа для географической точки*/
public class ClosestPointFinder {
    private final GeographicPoint geographicPoint;
    private Node closestPoint;
    private double distance = Double.MAX_VALUE;

    public ClosestPointFinder(GeographicPoint point){
        this.geographicPoint = point;
    }

    public GeographicPoint getGeographicPoint() {
        return geographicPoint;
    }

    public Node getClosestPoint() {
        return closestPoint;
    }

    public void put(Node node) {
        double distance = GeometryUtils.calculatePointToPointDistance(
                geographicPoint,
                new GeographicPoint(node));

        if (this.distance > distance){
            this.distance = distance;
            closestPoint = node;
        }
    }
}
