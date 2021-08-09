package mapObjects.parseConfigurations;

import algorithms.pointsFinders.PointFinderTree;
import algorithms.pointsFinders.QuadTree;
import mapObjects.GeographicPoint;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;

/**Класс выступает в качестве конфигураций к определению участка дороги как заблокированной*/
public class BlockedPointConfiguration {
    private final QuadTree tree;

    public BlockedPointConfiguration(){
        tree = new PointFinderTree(55.63213647883612, 52.201261423294824,55.81003367465946, 52.605008981888574, 0);
    }

    public BlockedPointConfiguration(Path configFile) throws ParserConfigurationException, IOException, SAXException {
        tree = new PointFinderTree(55.63213647883612, 52.201261423294824,55.81003367465946, 52.605008981888574, 4);
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

            tree.add(new GeographicPoint(lat, lon));
        }
    }

    //Если будет найдена точка, то значит заблокирована
    public boolean isBlocked(mapObjects.Node node){
        return tree.find(node) != null;
    }
}
