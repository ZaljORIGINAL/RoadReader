package mapObjects;

/**Класс представляет географическую точку на карте.*/
public class Node {
    private final long id;
    private final double lat;
    private final double lon;

    public Node(long id, double lat, double lon){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public long getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
