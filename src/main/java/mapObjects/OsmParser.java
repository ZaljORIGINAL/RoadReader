package mapObjects;

import mapObjects.parseConfigurations.BlockedPointConfiguration;
import mapObjects.parseConfigurations.MaxSpeedConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import algorithms.Graph;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static mapObjects.OsmParseHelper.*;

public class OsmParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(OsmParser.class);

    private final Document document;
    private MaxSpeedConfiguration maxSpeedConfiguration;
    private BlockedPointConfiguration blockedPointConfiguration;

    public OsmParser(Path osmFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(osmFile.toFile());

        maxSpeedConfiguration = new MaxSpeedConfiguration();
        blockedPointConfiguration = new BlockedPointConfiguration();
    }

    /**Метод считает из документа все автомобильные дороги
     * @return Map дорог, в качестве ключа id дороги, в качестве значения объект дороги.*/
    public Set<Way> getWays() {
        Set<Way> ways = new HashSet<>();
        int counter = 0;
        NodeList waysElements = document.getDocumentElement().getChildNodes();
        for (int wayIndex = 0; wayIndex < waysElements.getLength(); wayIndex++) {
            Node wayElement = waysElements.item(wayIndex);

            if (wayElement.getNodeType() != Node.ELEMENT_NODE
                    || wayElement.getNodeName().equals("bounds")
                    || wayElement.getNodeName().equals(NODE_TAG))
                continue;

            if (wayElement.getNodeName().equals("relation")) {
                LOGGER.info("Всего дорог " + counter);
                break;
            }

            //По умолчанию считаем, что макс скорость 60 км/ч
            double speed = NO_ROAD_SPEED;
            //По умолчанию считаем, что двухстороннее движение
            boolean oneWay = false;
            List<Long> nodeIdList = new ArrayList<>();

            NodeList objectFields = wayElement.getChildNodes();
            for (int fieldIndex = objectFields.getLength() - 1; fieldIndex >= 0 ; fieldIndex--){
                Node field = objectFields.item(fieldIndex);
                if (field.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                NamedNodeMap fieldAttrMap = field.getAttributes();
                if (field.getNodeName().equals(TAG)){
                    String attrName = fieldAttrMap.getNamedItem(KEY_ATTRIBUTE).getNodeValue();
                    //Проверка типа дороги
                    if (attrName.equals(HIGHWAY_FIELD)){
                        String value = fieldAttrMap.getNamedItem(VALUE_ATTRIBUTE).getNodeValue();
                        speed = maxSpeedConfiguration.getSpeed(value);
                        if (speed == NO_ROAD_SPEED)
                            break;

                        //Получение направления движения по дороге
                    }else if (attrName.equals(ONEWAY_FIELD)){
                        String value = fieldAttrMap.getNamedItem(VALUE_ATTRIBUTE).getNodeValue();
                        if (value.equals("yes"))
                            oneWay = true;
                    }
                }else if (speed != NO_ROAD_SPEED){
                    long nodeId = Long.parseLong(fieldAttrMap.getNamedItem(REF_ATTRIBUTE).getNodeValue());
                    nodeIdList.add(nodeId);
                }
            }

            if (speed == NO_ROAD_SPEED)
                continue;

            int id = Integer.parseInt(wayElement.getAttributes().getNamedItem(ID_ATTRIBUTE).getNodeValue());
            Collections.reverse(nodeIdList);
            Way way = new Way(id, nodeIdList, speed, oneWay);
            ways.add(way);
            counter++;
            if (counter % 1_000 == 0)
                LOGGER.info("Обработано " + counter +" дорог");
        }

        return ways;
    }

    /**Метод считает из документа параметры точек и вернет в качестве объекта точки
     * @param nodesIds - список точек, по которым требуется получить их объекты
     * @return Map точек, в качестве ключа id точки, в качестве значения объект точки.*/
    public Set<mapObjects.Node> getNodes(Set<Long> nodesIds) {
        Set<mapObjects.Node> nodesObjectsMap = new HashSet<>();
        NodeList nodesElements = document.getDocumentElement().getChildNodes();
        int counter = 0;
        for (int nodeIndex = 0; nodeIndex < nodesElements.getLength(); nodeIndex++) {
            Node nodeElement = nodesElements.item(nodeIndex);
            if (nodeElement.getNodeType() != Node.ELEMENT_NODE || nodeElement.getNodeName().equals("bounds"))
                continue;

            if (nodeElement.getNodeName().equals("way"))
                break;

            NamedNodeMap attributes = nodeElement.getAttributes();
            long id;
            double lat;
            double lon;

            //Получаем id
            id = Long.parseLong(attributes.getNamedItem(ID_ATTRIBUTE).getNodeValue());

            if (!nodesIds.contains(id))
                continue;

            //Получаем lat
            lat = Double.parseDouble(attributes.getNamedItem(LAT_ATTRIBUTE).getNodeValue());

            //Получаем lon
            lon = Double.parseDouble(attributes.getNamedItem(LON_ATTRIBUTE).getNodeValue());

            nodesObjectsMap.add(new mapObjects.Node(id, lat, lon));
            counter++;
            if (counter % 10_000 == 0 || counter == nodesIds.size())
                LOGGER.info("Обработано " + counter +" из " + nodesIds.size());
        }

        return nodesObjectsMap;
    }

    public void setMaxSpeedConfiguration(MaxSpeedConfiguration configuration) {
        maxSpeedConfiguration = configuration;
    }

    public void setBlockedPointConfiguration(BlockedPointConfiguration configuration) {
        this.blockedPointConfiguration = configuration;
    }

    /** Метод преобразует переданные дороги в граф, для последующего его использования в алгоритмах.
     * @param ways , в качестве ключа id дороги, в качестве значения объект дороги.
     * @return объект графа.*/
    public Graph convertToGraph(Set<Way> ways) {
        Set<Long> waysNodesId = new HashSet<>();
        Set<Long> towerNodesId = new HashSet<>();

        for (Way way : ways) {
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

        Set<mapObjects.Node> wayNodesObject = getNodes(waysNodesId);
        LOGGER.info("Количество полученных объектов точек: " + wayNodesObject.size());

        return new Graph(wayNodesObject, towerNodesId, ways);
    }
}
