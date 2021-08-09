package algorithms.dijkstra.WeightTypes;

import mapObjects.Edge;
import algorithms.dijkstra.WeightСalculator;

/**Класс служит для извлечения из графа параметра времени на преодоление грани и выдачи ее как веса грани.*/
public class TimeWeighting extends WeightСalculator {

    /**Вернет время затрачиваемое на преодоление грани, как вес грани.*/
    @Override
    public double calculate(Edge edge) {
        return edge.getTime();
    }
}
