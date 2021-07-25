package MapObjects;

/**Класс представляет дистанцию между двумя географическими точками (Point).*/
public class Distance {
    private final Point start;
    private final Point finish;
    private int weight;

    public Distance(Point start, Point finish, int weight){
        this.start = start;
        this.finish = finish;
        this.weight = weight;
    }


    public Point getStart() {
        return start;
    }

    public Point getFinish() {
        return finish;
    }

    public int getWeight() {
        return weight;
    }
}
