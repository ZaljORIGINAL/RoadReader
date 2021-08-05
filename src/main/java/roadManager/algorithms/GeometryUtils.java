package roadManager.algorithms;

import mapObjects.Node;

/**Класс предоставляет static методы для проведения геометрических измерений над точками.*/
public class GeometryUtils {

        /**Вычисление расстояния между двумя точками на поверхности*/
        public static double calculatePointToPointDistance(GeographicPoint node1, GeographicPoint node2){
            double R = 6378137;
            double C = Math.PI/180;

            double X1 = Math.cos(C * node1.getLat()) * Math.cos(C * node1.getLon());
            double X2 = Math.cos(C * node2.getLat()) * Math.cos(C * node2.getLon());

            double Y1 = Math.cos(C * node1.getLat()) * Math.sin(C * node1.getLon());
            double Y2 = Math.cos(C * node2.getLat()) * Math.sin(C * node2.getLon());

            double Z1 = Math.sin(C * node1.getLat());
            double Z2 = Math.sin(C * node2.getLat());

            return R * Math.acos(X1 * X2 + Y1 * Y2 + Z1 * Z2);
        }
}
