package roadManager.algorithms;

import mapObjects.Node;

/**Класс представляет географическую точку: latitude, longitude.*/
public class GeographicPoint {
    private final double lat;
    private final double lon;

    public GeographicPoint(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public GeographicPoint(Node node){
        this.lat = node.getLat();
        this.lon = node.getLon();
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
