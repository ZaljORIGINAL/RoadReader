package algorithms.pointsFinders;

import mapObjects.GeographicPoint;

import java.util.ArrayList;

import static algorithms.GeometryUtils.calculatePointToPointDistance;

public class ClosestPointFinderTree extends QuadTree<ClosestPointFinderTree>{
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

    @Override
    protected ClosestPointFinderTree getChildren(double minLat, double minLon, double maxLat, double maxLon, int level) {
        return new ClosestPointFinderTree(minLat, minLon, maxLat, maxLon, level);
    }
}
