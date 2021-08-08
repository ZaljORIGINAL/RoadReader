package mapObjects.parseConfigurations;

import mapObjects.GeographicPoint;
import mapObjects.Node;

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
