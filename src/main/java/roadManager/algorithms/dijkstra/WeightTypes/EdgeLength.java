package roadManager.algorithms.dijkstra.WeightTypes;

import mapObjects.Edge;
import roadManager.algorithms.dijkstra.SelectedWeight;

/** Класс служит для извлечения из графа параметра длины грани и выдачи ее как веса грани.*/
public class EdgeLength extends SelectedWeight {

    /**Вернет длину грани, как вес грани.*/
    @Override
    public double getWeight(Edge edge) {
        return edge.getLength();
    }
}
