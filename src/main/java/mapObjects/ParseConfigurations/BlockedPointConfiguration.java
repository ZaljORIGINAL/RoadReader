package mapObjects.ParseConfigurations;

import mapObjects.Node;
import roadManager.algorithms.GeographicPoint;

import java.nio.file.Path;
import java.util.List;

/**Класс выступает в качестве конфигураций к определению участка дороги как заблокированной*/
public class BlockedPointConfiguration {
    private List<GeographicPoint> points;

    public BlockedPointConfiguration(Path configFile){

    }

    public boolean check(List<Node> nodes){

        return false;
    }
}
