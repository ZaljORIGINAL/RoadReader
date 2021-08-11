package algorithms.dijkstra.WeightTypes;

import mapObjects.Edge;
import algorithms.dijkstra.WeightСalculator;

public class TimeWeighting extends WeightСalculator {

    /**Вернет время затрачиваемое на преодоление грани, как вес грани.*/
    @Override
    public double calculate(Edge edge) {
        return edge.getTime();
    }
}
