package mapObjects;

/**Класс представляет географическую точку на карте.*/
public class Node {
    private final int id;
    private String name;
    //private int coordinateX;
    //private int coordinateY;

    public Node(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
