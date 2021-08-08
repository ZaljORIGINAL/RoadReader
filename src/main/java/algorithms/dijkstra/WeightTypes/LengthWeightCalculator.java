package algorithms.dijkstra.WeightTypes;

import mapObjects.Edge;
import algorithms.dijkstra.WeightСalculator;

/** Класс служит для извлечения из графа параметра длины грани и выдачи ее как веса грани.*/
public class LengthWeightCalculator extends WeightСalculator {

    /**Вернет длину грани, как вес грани.*/
    @Override
    public double calculate(Edge edge) {
        return edge.getLength();
    }
}
