package roadManager.algorithms.closestPointCalculator;

import mapObjects.Node;
import roadManager.algorithms.GeographicPoint;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**Класс предоставляет функционал по поиску ближайшей точки.*/
public class ClosestPointCalculator {
    private Map<Long, Node> nodes;

    /**
     * @param nodes - коллекция точек, среди которых требуется провести поиск наиближайшей точки*/
    public ClosestPointCalculator(Map<Long, Node> nodes){
        this.nodes = nodes;
    }

    /**Метод для поиска наиближайшей точки для географической координаты.
     * @param nodes - коллекция географфических точек (координата точки)
     * @return Пара географическая точка и ближайшая к ней точка графа.*/
    public Map<GeographicPoint, Node> calculate(List<GeographicPoint> nodes){
        //Создаем экземпляры классов отвечающих за поиск наиблежайше точки.
        List<ClosestPointFinder> pointFinders = nodes.stream()
                .map(ClosestPointFinder::new)
                .collect(Collectors.toList());

        //Перебираем кажду точку в графе и отправляем ее на проверку.
        for (Map.Entry<Long, Node> entry : this.nodes.entrySet()) {
            for (ClosestPointFinder finder : pointFinders){
                finder.put(entry.getValue());
            }
        }

        //Запрашиваем у поисковиков результаты
        return pointFinders.stream()
                .collect(Collectors.toMap(
                        ClosestPointFinder::getGeographicPoint,
                        ClosestPointFinder::getClosestPoint));
    }
}
