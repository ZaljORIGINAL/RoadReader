package roadManager;

import mapObjects.Edge;
import mapObjects.Node;

import java.util.*;

/**Класс представляет маршрут от стартовой точки до конечной*/
public class Route {
    private final List<Node> nodes;
    //Длина измеряется в метрах.
    private final double length;
    //Время измеряется в минутах.
    private final double time;

    public Route(List<Edge> edges, long startNodeId){
        nodes = new ArrayList<>();
        double length = 0;
        double time = 0;

        for (int index = 0; index < edges.size(); index++){
            Edge edge = edges.get(index);
            List<Node> nodes = new ArrayList<>(edge.getNodes());
            if (nodes.get(0).getId() != startNodeId)
                Collections.reverse(nodes);
            this.nodes.addAll(nodes.subList(0, nodes.size()-1));

            if (index == edges.size() - 1)
                this.nodes.add(nodes.get(nodes.size()-1));

            startNodeId = nodes.get(nodes.size() - 1).getId();

            length += edge.getLength();
            time += edge.getTime();
        }

        this.length = length;
        this.time = time;
    }

    /**Возвращает список последовательных точек маршрута.*/
    public List<Node> getNodes() {
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
