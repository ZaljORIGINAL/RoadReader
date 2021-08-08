package mapObjects;

/**Класс представляет географическую точку на карте.*/
public class Node extends GeographicPoint {
    private final long id;

    public Node(long id, double lat, double lon){
        super(lat, lon);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
