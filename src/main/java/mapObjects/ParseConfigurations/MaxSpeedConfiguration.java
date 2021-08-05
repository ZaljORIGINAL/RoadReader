package mapObjects.ParseConfigurations;

import mapObjects.OsmParseHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**Класс выступает в качестве конфигураций к выставлению максимальной скорости для определенного типа дороги*/
public class MaxSpeedConfiguration {
    private final Map<String, Double> speedsMap = new HashMap<>();

    public MaxSpeedConfiguration(){}

    public MaxSpeedConfiguration(Path configFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(configFile.toFile());

        NodeList paramElements = document.getDocumentElement().getElementsByTagName(
                ConfigFileParse.PARAM_TAG);
        for (int paramIndex = 0; paramIndex < paramElements.getLength(); paramIndex++){
            Element param = (Element) paramElements.item(paramIndex);

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

    public double getMaxSpeed(String wayType){
        Double speed = speedsMap.get(wayType);
        if (speed == null)
            return 60;

        return speed;
    }
}
