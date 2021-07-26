package MapObjects;

/**Класс представляет связь между двумя географическими точками (Point).*/
public class Edge {
    private final Node start;
    private final Node finish;
    private int length;

    public Edge(Node start, Node finish, int length){
        this.start = start;
        this.finish = finish;
        this.length = length;
    }


    public Node getStart() {
        return start;
    }

    public Node getFinish() {
        return finish;
    }

    public int getLength() {
        return length;
    }
}
