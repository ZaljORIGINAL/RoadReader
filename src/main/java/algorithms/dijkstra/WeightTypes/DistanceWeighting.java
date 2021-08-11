package algorithms.dijkstra.WeightTypes;

import mapObjects.Edge;
import algorithms.dijkstra.WeightСalculator;

public class DistanceWeighting extends WeightСalculator {

    /**Вернет длину грани, как вес грани.*/
    @Override
    public double calculate(Edge edge) {
        return edge.getLength();
    }
}
