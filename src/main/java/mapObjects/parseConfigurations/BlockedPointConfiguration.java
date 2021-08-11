package mapObjects.parseConfigurations;

import algorithms.pointsFinders.QuadTree;
import mapObjects.GeographicPoint;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**Класс выступает в качестве конфигураций к определению участка дороги как заблокированной*/
public class BlockedPointConfiguration {
    private final Set<Long> blockedNodesId = new HashSet<>();
    ;

    public BlockedPointConfiguration() {
    }

    public BlockedPointConfiguration(Path configFile, QuadTree nodeTree) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(configFile.toFile());

        NodeList params = document.getDocumentElement().getChildNodes();

        for (int paramIndex = 0; paramIndex < params.getLength(); paramIndex++){
            Node param = params.item(paramIndex);
            if (param.getNodeType() != org.w3c.dom.Node.ELEMENT_NODE || param.getNodeName().equals("bounds"))
                continue;

            NamedNodeMap paramAttributes = param.getAttributes();
            double lat;
            double lon;
            lat = Double.parseDouble(paramAttributes.getNamedItem(ConfigFileParse.LAT_ATTR)
                    .getNodeValue());
            lon = Double.parseDouble(paramAttributes.getNamedItem(ConfigFileParse.LON_ATTR)
                    .getNodeValue());

            mapObjects.Node node = nodeTree.findNode(new GeographicPoint(lat, lon));
            if (node != null)
                blockedNodesId.add(node.getId());
        }
    }

    public boolean isBlocked(long nodeId) {
        return blockedNodesId.contains(nodeId);
    }
}
