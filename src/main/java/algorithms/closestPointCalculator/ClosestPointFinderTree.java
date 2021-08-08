package algorithms.closestPointCalculator;

import mapObjects.GeographicPoint;

import java.util.ArrayList;

import static algorithms.GeometryUtils.calculatePointToPointDistance;

public class ClosestPointFinderTree extends QuadTree{
    public ClosestPointFinderTree(double minLat, double minLon, double maxLat, double maxLon, int level) {
        super(minLat, minLon, maxLat, maxLon, level);
    }

    @Override
    public GeographicPoint find(GeographicPoint point) {
        if (level != 0)
            return childrens.get(getChildIndex(point)).find(point);
        else{
            double minDistance = Double.MAX_VALUE;
            GeographicPoint closestNode = null;
            for (GeographicPoint gPoint : nodes) {
                double distance = calculatePointToPointDistance(gPoint, gPoint);
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

    @Override
    protected void generateChildrens() {
        childrens = new ArrayList<>();
        nodes = null;
        childrens.add(new ClosestPointFinderTree(
                averageLat, minLon,
                maxLat, averageLon,
                level -1));

        childrens.add(new ClosestPointFinderTree(
                averageLat, averageLon,
                maxLat, maxLon,
                level - 1));

        childrens.add(new ClosestPointFinderTree(
                minLat, minLon,
                averageLat, averageLon,
                level -1));

        childrens.add(new ClosestPointFinderTree(
                minLat, averageLon,
                averageLat, maxLon,
                level - 1));
    }
}
