package roadManager.algorithms.dijkstra.WeightTypes;

import mapObjects.Edge;
import roadManager.algorithms.dijkstra.SelectedWeight;

/**Класс служит для извлечения из графа параметра времени на преодоление грани и выдачи ее как веса грани.*/
public class EdgeTime extends SelectedWeight {

    /**Вернет время затрачиваемое на преодоление грани, как вес грани.*/
    @Override
    public double getWeight(Edge edge) {
        return edge.getTime();
    }
}
