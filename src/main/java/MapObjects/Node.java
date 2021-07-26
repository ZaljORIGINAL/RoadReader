package MapObjects;

import java.util.ArrayList;
import java.util.List;

/**Класс представляет географическую точку на карте.*/
public class Node {
    private final int id;
    private String name;
    //private int coordinateX;
    //private int coordinateY;
    private ArrayList<Edge> edges;

    public Node(int id, String name){
        this.id = id;
        this.name = name;

        edges = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Edge> getEdges(){
        return edges;
    }

    //TODO Создать методы возвращающие пути от точки и к точке (всего 2 метода)

    public void addEdge(Edge edge){
        edges.add(edge);
    }
}
