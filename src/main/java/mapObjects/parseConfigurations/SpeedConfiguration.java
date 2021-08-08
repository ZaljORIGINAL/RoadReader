package mapObjects.parseConfigurations;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static mapObjects.OsmParseHelper.*;

/**Класс выступает в качестве конфигураций к выставлению максимальной скорости для определенного типа дороги*/
public class SpeedConfiguration {
    private final Map<String, Double> speedsMap = getDefaultSpeedMap();

    public SpeedConfiguration(){}

    public SpeedConfiguration(Path configFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(configFile.toFile());

        NodeList paramElements = document.getDocumentElement().getChildNodes();
        for (int paramIndex = 0; paramIndex < paramElements.getLength(); paramIndex++){
            Node param = paramElements.item(paramIndex);
            if (param.getNodeType() != org.w3c.dom.Node.ELEMENT_NODE || param.getNodeName().equals("bounds"))
                continue;

            NamedNodeMap paramAttributes = param.getAttributes();
            String wayType;
            double maxSpeed;
            wayType = paramAttributes.getNamedItem(ConfigFileParse.KEY_ATTR)
                    .getNodeValue();
            maxSpeed = Double.parseDouble(paramAttributes.getNamedItem(ConfigFileParse.VALUE_ATTR)
                    .getNodeValue());

            speedsMap.put(wayType, maxSpeed);
        }
    }

    public double getSpeed(String wayType) {
        return speedsMap.getOrDefault(wayType, NO_ROAD_SPEED);
    }

    private static Map<String, Double> getDefaultSpeedMap() {
        Map<String, Double> waysTypes = new HashMap<>();
        waysTypes.put(MOTORWAY_TYPE, 60.0);
        waysTypes.put(TRUNK_TYPE, 60.0);
        waysTypes.put(PRIMARY_TYPE, 60.0);
        waysTypes.put(SECONDARY_TYPE, 60.0);
        waysTypes.put(TERTIARY_TYPE, 60.0);
        waysTypes.put(UNCLASSIFIED_TYPE, 60.0);
        waysTypes.put(RESIDENTIAL_TYPE, 60.0);
        waysTypes.put(MOTORWAY_LINK_TYPE, 60.0);
        waysTypes.put(TRUNK_LINK_TYPE, 60.0);
        waysTypes.put(PRIMARY_LINK_TYPE, 60.0);
        waysTypes.put(SECONDARY_LINK_TYPE, 60.0);
        waysTypes.put(TERTIARY_LINK_TYPE, 60.0);
        waysTypes.put(LIVING_STREET_TYPE, 60.0);
        waysTypes.put(SERVICE_TYPE, 60.0);
        waysTypes.put(TRACK_TYPE, 60.0);
        return  waysTypes;
    }
}
