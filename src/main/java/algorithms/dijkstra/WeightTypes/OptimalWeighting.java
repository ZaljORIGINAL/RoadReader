package algorithms.dijkstra.WeightTypes;

import algorithms.dijkstra.WeightСalculator;
import mapObjects.Edge;

public class OptimalWeighting extends WeightСalculator {
    @Override
    public double calculate(Edge edge) {
        return edge.getTime() * 60 * 1000 + edge.getLength() * 0.05;
    }
}
