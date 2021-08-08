package algorithms.dijkstra;

import mapObjects.Edge;

/**Класс служит для упрощенного доступа к параметрам грани и выборки как веса для работы алгоритма.*/
public abstract class WeightСalculator {

    /**Возвращает вес переданной грани*/
    public abstract double calculate(Edge edge);
}
