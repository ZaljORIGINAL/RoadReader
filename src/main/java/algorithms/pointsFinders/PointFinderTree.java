package algorithms.pointsFinders;

import mapObjects.GeographicPoint;

import java.util.ArrayList;

public class PointFinderTree extends QuadTree<PointFinderTree>{
    public PointFinderTree(double minLat, double minLon, double maxLat, double maxLon, int level) {
        super(minLat, minLon, maxLat, maxLon, level);
    }

    @Override
    public GeographicPoint find(GeographicPoint point) {
        if (level != 0)
            return childrens.get(getChildIndex(point)).find(point);
        else{
            GeographicPoint closestNode = null;
            for (GeographicPoint gPoint : nodes) {
                if (gPoint.getLat() == point.getLat() && gPoint.getLon() == point.getLon()) {
                    return gPoint;
                }
            }
            return closestNode;
        }
    }

    @Override
    protected PointFinderTree getChildren(double minLat, double minLon, double maxLat, double maxLon, int level) {
        return new PointFinderTree(minLat, minLon, maxLat, maxLon, level);
    }
}
