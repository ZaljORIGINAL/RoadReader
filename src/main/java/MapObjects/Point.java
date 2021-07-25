package MapObjects;

import java.util.ArrayList;
import java.util.List;

/**Класс представляет географическую точку на карте.*/
public class Point {
    private final int id;
    private String name;
    //private int coordinateX;
    //private int coordinateY;
    private ArrayList<Distance> distances;

    public Point(int id, String name){
        this.id = id;
        this.name = name;

        distances = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Distance> getDistances(){
        return distances;
    }

    //TODO Создать методы возвращающие пути от точки и к точке (всего 2 метода)

    public void addDistance(Distance distance){
        distances.add(distance);
    }
}
