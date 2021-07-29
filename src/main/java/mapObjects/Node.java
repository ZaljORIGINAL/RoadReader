package mapObjects;

/**Класс представляет географическую точку на карте.*/
public class Node {
    private final long id;
    private String name;
    //private int coordinateX;
    //private int coordinateY;

    public Node(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
