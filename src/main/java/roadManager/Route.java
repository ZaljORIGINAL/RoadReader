package roadManager;

import mapObjects.Edge;
import mapObjects.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**Класс представляет маршрут от стартовой точки до конечной*/
public class Route {
    private final Set<Node> nodes;
    //Длина измеряется в метрах.
    private final double length;
    //Время измеряется в минутах.
    private final double time;

    public Route(List<Edge> edges){
        nodes = new HashSet<>();
        double length = 0;
        double time = 0;
        for (Edge edge : edges){
            List<Node> nodes = edge.getNodes();
            this.nodes.addAll(nodes);
            length += edge.getLength();
            time += edge.getTime();
        }

        this.length = length;
        this.time = time;
    }

    /**Возвращает список последовательных точек маршрута.*/
    public Set<Node> getNodes() {
        return nodes;
    }

    /**Возвращает длину маршрута.*/
    public double getLength() {
        return length;
    }

    /**Возвращает предполагаемое время на преодоление маршрута.*/
    public double getTime() {
        return time;
    }

    /**Получение информации по маршруту: Длина маршрута, время на преодоление, список последовательных точек.*/
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Расстояние: " + getLength() + "\n");
        str.append("Время на преодоление: " + getTime() + "\n");
        str.append("Двигайтесь по точкам: \n");
        nodes.stream()
                .forEach(node -> str.append("\tid: " + node.getId() + "\n"));

        return str.toString();
    }
}
