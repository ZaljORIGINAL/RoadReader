package mapObjects;

import mapObjects.Node;

/**Класс представляет географическую точку: latitude, longitude.*/
public class GeographicPoint {
    protected final double lat;
    protected final double lon;

    public GeographicPoint(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String toWktString() {
        return lon + " " + lat;
    }

    @Override
    public String toString() {
        return "Node(" + lon + ", " + lat + ")";
    }
}
