package algorithms.pointsFinders;

import mapObjects.GeographicPoint;

import java.util.ArrayList;

public class PointFinderTree extends QuadTree{
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
    protected void generateChildrens() {
        childrens = new ArrayList<>();
        nodes = null;
        childrens.add(new PointFinderTree(
                averageLat, minLon,
                maxLat, averageLon,
                level -1));

        childrens.add(new PointFinderTree(
                averageLat, averageLon,
                maxLat, maxLon,
                level - 1));

        childrens.add(new PointFinderTree(
                minLat, minLon,
                averageLat, averageLon,
                level -1));

        childrens.add(new PointFinderTree(
                minLat, averageLon,
                averageLat, maxLon,
                level - 1));
    }
}
