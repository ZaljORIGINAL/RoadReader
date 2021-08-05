package mapObjects;

import mapObjects.ParseConfigurations.MaxSpeedConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import roadManager.algorithms.Graph;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class OsmParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(OsmParser.class);

    private final Document document;
    private MaxSpeedConfiguration maxSpeedConfiguration;

    public OsmParser(Path osmFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(osmFile.toFile());

        maxSpeedConfiguration = new MaxSpeedConfiguration();
    }

    /**Метод считает из документа все автомобильные дороги
     * @return Map дорог, в качестве ключа id дороги, в качестве значения объект дороги.*/
    public Map<Long, Way> getWays() {
        Map<Long, Way> waysMap = new HashMap<>();

        NodeList waysElements = document.getDocumentElement().getElementsByTagName(OsmParseHelper.WAY_TAG);
        for (int wayIndex = 0; wayIndex < waysElements.getLength(); wayIndex++) {
            Element wayElement = (Element) waysElements.item(wayIndex);

            NamedNodeMap wayAttributes = wayElement.getAttributes();

            //По умолчанию считаем, что это не автодорога
            int wayType = -1;
            //По умолчанию считаем, что макс скорость 60 км/ч
            double maxSpeed = 60;
            //По умолчанию считаем, что двухстороннее движение
            boolean oneWay = false;
            NodeList objectFields = wayElement.getElementsByTagName(OsmParseHelper.OTHER_TAG);
            for (int fieldIndex = 0; fieldIndex < objectFields.getLength(); fieldIndex++){
                Node field = objectFields.item(fieldIndex);
                NamedNodeMap fieldAttrMap = field.getAttributes();
                String attrName = fieldAttrMap.getNamedItem(OsmParseHelper.KEY_ATTRIBUTE).getNodeValue();
                //Проверка типа дороги
                if (attrName.equals(OsmParseHelper.HIGHWAY_FIELD)){
                    String value = fieldAttrMap.getNamedItem(
                            OsmParseHelper.VALUE_ATTRIBUTE).getNodeValue();
                    wayType = getIdWayType(value);
                    if (wayType != -1){
                        maxSpeed = maxSpeedConfiguration.getMaxSpeed(value);
                    }else
                        break;

                //Получение направления движения по дороге
                }else if (attrName.equals(OsmParseHelper.ONEWAY_FIELD)){
                    String value = fieldAttrMap.getNamedItem(
                            OsmParseHelper.VALUE_ATTRIBUTE).getNodeValue();
                    if (value.equals("yes"))
                        oneWay = true;
                }
            }
            //В случае если это не автодорога, то отменяем чтение
            if (wayType == -1)
                continue;

            int id = Integer.parseInt(wayAttributes.getNamedItem(OsmParseHelper.ID_ATTRIBUTE).getNodeValue());

            //Получение геометрии дороги (точки)
            NodeList nodesList = wayElement.getElementsByTagName(OsmParseHelper.LINK_TO_NODE_TAG);
            List<Long> nodeIdList = new ArrayList<>();
            for (int nodeIndex = 0; nodeIndex < nodesList.getLength(); nodeIndex++){
                Node idNode = nodesList.item(nodeIndex);
                Node idAttribute = idNode.getAttributes().getNamedItem(OsmParseHelper.REF_ATTRIBUTE);
                long nodeId = Long.parseLong(idAttribute.getNodeValue());
                nodeIdList.add(nodeId);
            }

            Way way = new Way(id, nodeIdList, wayType, maxSpeed, oneWay);
            waysMap.put(way.getId(), way);
        }

        return waysMap;
    }

    /**Метод считает из документа параметры точек и вернет в качестве объекта точки
     * @param nodesIds - список точек, по которым требуется получить их объекты
     * @return Map точек, в качестве ключа id точки, в качестве значения объект точки.*/
    public Map<Long, mapObjects.Node> getNodes(Set<Long> nodesIds){
        Map<Long, mapObjects.Node> nodesObjectsMap = new HashMap<>();

        NodeList nodesElements = document.getDocumentElement().getElementsByTagName(OsmParseHelper.NODE_TAG);

        for (int nodeIndex = 0; nodeIndex < nodesElements.getLength(); nodeIndex++) {
            LOGGER.info("Обрабатывается " + nodeIndex +" из " + nodesElements.getLength());
            Element nodeElement = (Element) nodesElements.item(nodeIndex);

            NamedNodeMap fieldAttrMap = nodeElement.getAttributes();
            long id;
            double lat;
            double lon;

            //Получаем id
            String idStr = fieldAttrMap.getNamedItem(OsmParseHelper.ID_ATTRIBUTE)
                    .getNodeValue();
            id = Long.parseLong(idStr);

            if (!nodesIds.contains(id))
                continue;

            //Получаем lat
            String latStr = fieldAttrMap.getNamedItem(OsmParseHelper.LAT_ATTRIBUTE)
                    .getNodeValue();
            lat = Double.parseDouble(latStr);

            //Получаем lon
            String lonStr = fieldAttrMap.getNamedItem(OsmParseHelper.LON_ATTRIBUTE)
                    .getNodeValue();
            lon = Double.parseDouble(lonStr);

            nodesObjectsMap.put(
                    id,
                    new mapObjects.Node(id, lat, lon)
            );
        }

        return nodesObjectsMap;
    }

    public void setMaxSpeedConfiguration(MaxSpeedConfiguration configuration){
        maxSpeedConfiguration = configuration;
    }

    /** Метод преобразует переданные дороги в граф, для последующего его использования в алгоритмах.
     * @param waysMap , в качестве ключа id дороги, в качестве значения объект дороги.
     * @return объект графа.*/
    public Graph convertToGraph(Map<Long, Way> waysMap) {
        Set<Long> waysNodesId = new HashSet<>();
        Set<Long> towerNodesId = new HashSet<>();

        for (Map.Entry<Long, Way> entry : waysMap.entrySet()) {
            Way way = entry.getValue();
            List<Long> nodes = way.getNodes();
            //Устанавливаем первую и последнюю точку как ключевые;
            waysNodesId.add(nodes.get(0));
            waysNodesId.add(nodes.get(nodes.size()-1));

            //Обработка остальных точек //обычный for
            for (long nodeId : nodes) {
                if (waysNodesId.contains(nodeId))
                    towerNodesId.add(nodeId);
                else
                    waysNodesId.add(nodeId);
            }
        }
        LOGGER.info("Количество ключевых точек: " + towerNodesId.size());

        Map<Long, mapObjects.Node> wayNodesObject = getNodes(waysNodesId);
        LOGGER.info("Количество полученных объектов точек: " + wayNodesObject.size());

        return new Graph(wayNodesObject, towerNodesId, waysMap);
    }

    private int getIdWayType(String namedItem) {
        List<String> wayTypes = OsmParseHelper.getWayTypes();
        return wayTypes.indexOf(namedItem);
    }
}
