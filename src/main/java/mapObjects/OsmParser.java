package mapObjects;

import org.w3c.dom.*;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OsmParser {
    private final Document document;

    public OsmParser(Path osmFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(osmFile.toFile());
    }

    public Map<Integer, Way> getWays(){
        Map<Integer, Way> waysMap = new HashMap<>();

        NodeList waysElements = document.getDocumentElement().getElementsByTagName(ParseHelper.WAY_TAG);
        for (int wayIndex = 0; wayIndex < waysElements.getLength(); wayIndex++) {
            Element wayElement = (Element) waysElements.item(wayIndex);

            NamedNodeMap wayAttributes = wayElement.getAttributes();
            int id = Integer.parseInt(wayAttributes.getNamedItem(ParseHelper.ID_ATTRIBUTE).getNodeValue());

            boolean isMotorRoad = true;
            //По умолчанию считаем односторонне движение
            byte wayDirection = 0;
            NodeList objectFields = wayElement.getElementsByTagName(ParseHelper.OTHER_TAG);
            for (int fieldIndex = 0; fieldIndex < objectFields.getLength(); fieldIndex++){
                Node field = objectFields.item(fieldIndex);
                NamedNodeMap fieldAttrMap = field.getAttributes();
                String fieldName = fieldAttrMap.getNamedItem(ParseHelper.KEY_ATTRIBUTE).getNodeValue();
                //Проверка типа дороги
                if (fieldName.equals(ParseHelper.HIGHWAY_FIELD)){
                    if (!isMotorRoad(
                            fieldAttrMap.getNamedItem(
                                    ParseHelper.VALUE_ATTRIBUTE).getNodeValue())){
                        isMotorRoad = false;
                        break;
                    }
                //Получение направления движения по дороге
                }else if (fieldName.equals(ParseHelper.ONEWAY_FIELD)) {
                    wayDirection = ParseHelper.getWayDirectionType(
                            fieldAttrMap.getNamedItem(
                                    ParseHelper.VALUE_ATTRIBUTE).getNodeValue());
                }
            }
            //В случае если это не автодорога, то отменяем чтение
            if (!isMotorRoad)
                continue;

            //Получение геометрии дороги
            NodeList nodesList = wayElement.getElementsByTagName(ParseHelper.LINK_TO_NODE_TAG);
            List<Integer> nodeIdList = new ArrayList<>();
            for (int nodeIndex = 0; nodeIndex < nodesList.getLength(); nodeIndex++){
                Node idNode = nodesList.item(nodeIndex);
                Node idAttribute = idNode.getAttributes().getNamedItem(ParseHelper.REF_ATTRIBUTE);
                int nodeId = Integer.parseInt(idAttribute.getNodeValue());
                nodeIdList.add(nodeId);
            }

            Way way = new Way(id, nodeIdList, wayDirection);
            waysMap.put(way.getId(), way);
        }

        return waysMap;
    }

    private boolean isMotorRoad(String namedItem) {
        for (String type : ParseHelper.MOTOR_ROADS_LIST) {
            if (type.equals(namedItem))
                return true;
        }

        return false;
    }
}
