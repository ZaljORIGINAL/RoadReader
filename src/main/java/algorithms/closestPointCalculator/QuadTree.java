package algorithms.closestPointCalculator;

import mapObjects.GeographicPoint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static algorithms.GeometryUtils.calculatePointToPointDistance;

public abstract class QuadTree {
    protected static int pointsCount = 0;
    protected final double minLat;
    protected final double minLon;
    protected final double maxLat;
    protected final double maxLon;
    protected final double averageLat;
    protected final double averageLon;
    protected List<QuadTree> childrens;
    protected Set<GeographicPoint> nodes;
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
            generateChildrens();
        } else {
            childrens = null;
            nodes = new HashSet<>();
        }

    }

    public void add(GeographicPoint point) {
        if (level != 0)
            childrens.get(getChildIndex(point)).add(point);
        else{
            nodes.add(point);
            pointsCount++;
        }
    }

    public abstract GeographicPoint find(GeographicPoint point);

    protected int getChildIndex(GeographicPoint point) {
        return (point.getLat() > averageLat ? 0 : 2) + (point.getLon() > averageLon ? 1 : 0);
    }

    protected abstract void generateChildrens();
}
