package mapObjects;

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
import java.util.stream.Collectors;

public class OsmParser {
    private final Document document;

    public OsmParser(Path osmFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(osmFile.toFile());
    }

    public Map<Long, Way> getWays() {
        Map<Long, Way> waysMap = new HashMap<>();

        NodeList waysElements = document.getDocumentElement().getElementsByTagName(ParseHelper.WAY_TAG);
        for (int wayIndex = 0; wayIndex < waysElements.getLength(); wayIndex++) {
            Element wayElement = (Element) waysElements.item(wayIndex);

            NamedNodeMap wayAttributes = wayElement.getAttributes();

            //По умолчанию считаем, что это не автодорога
            int wayType = -1;
            //По умолчанию считаем, что макс скорость 60 км/ч
            int maxSpeed = 60;
            NodeList objectFields = wayElement.getElementsByTagName(ParseHelper.OTHER_TAG);
            for (int fieldIndex = 0; fieldIndex < objectFields.getLength(); fieldIndex++){
                Node field = objectFields.item(fieldIndex);
                NamedNodeMap fieldAttrMap = field.getAttributes();
                String attrName = fieldAttrMap.getNamedItem(ParseHelper.KEY_ATTRIBUTE).getNodeValue();
                //Проверка типа дороги
                if (attrName.equals(ParseHelper.HIGHWAY_FIELD)){
                    String value = fieldAttrMap.getNamedItem(
                            ParseHelper.VALUE_ATTRIBUTE).getNodeValue();
                    wayType = getIdWayType(value);
                    if (wayType == -1){
                        break;
                    }
                //Получение направления движения по дороге
                }else if (attrName.equals(ParseHelper.MAX_SPEED_FIELD)){

                }
            }
            //В случае если это не автодорога, то отменяем чтение
            if (wayType == -1)
                continue;

            int id = Integer.parseInt(wayAttributes.getNamedItem(ParseHelper.ID_ATTRIBUTE).getNodeValue());

            //Получение геометрии дороги (точки)
            NodeList nodesList = wayElement.getElementsByTagName(ParseHelper.LINK_TO_NODE_TAG);
            List<Long> nodeIdList = new ArrayList<>();
            for (int nodeIndex = 0; nodeIndex < nodesList.getLength(); nodeIndex++){
                Node idNode = nodesList.item(nodeIndex);
                Node idAttribute = idNode.getAttributes().getNamedItem(ParseHelper.REF_ATTRIBUTE);
                long nodeId = Long.parseLong(idAttribute.getNodeValue());
                nodeIdList.add(nodeId);
            }

            Way way = new Way(id, nodeIdList, wayType, maxSpeed);
            waysMap.put(way.getId(), way);
        }

        return waysMap;
    }

    public Map<Long, mapObjects.Node> getNodes(Collection<Way> wayCollection){
        Map<Long, mapObjects.Node> nodesObjectsMap = new HashMap<>();

        NodeList waysElements = document.getDocumentElement().getElementsByTagName(ParseHelper.NODE_TAG);
        for (int wayIndex = 0; wayIndex < waysElements.getLength(); wayIndex++) {
            Element nodeElement = (Element) waysElements.item(wayIndex);

            NamedNodeMap fieldAttrMap = nodeElement.getAttributes();
            long id;
            double lat;
            double lon;

            //Получаем id
            String idStr = fieldAttrMap.getNamedItem(ParseHelper.ID_ATTRIBUTE)
                    .getNodeValue();
            id = Long.parseLong(idStr);

            boolean isWayNode = false;
            for (Way way : wayCollection) {
                List<Long> wayNodesId = way.getNodes();
                for (Long wayNodeId : wayNodesId) {
                    if (wayNodeId == id){
                        isWayNode = true;
                        break;
                    }
                }
            }

            if (isWayNode){
                //Получаем lat
                String latStr = fieldAttrMap.getNamedItem(ParseHelper.LAT_ATTRIBUTE)
                        .getNodeValue();
                lat = Double.parseDouble(latStr);

                //Получаем lon
                String lonStr = fieldAttrMap.getNamedItem(ParseHelper.LAT_ATTRIBUTE)
                        .getNodeValue();
                lon = Double.parseDouble(lonStr);

                nodesObjectsMap.put(
                        id,
                        new mapObjects.Node(id, lat, lon)
                );
            }
        }

        return nodesObjectsMap;
    }

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

        Map<Long, mapObjects.Node> wayNodesObject = getNodes(waysMap.values());
        Graph graph = new Graph(wayNodesObject, towerNodesId, waysMap);

        return graph;
    }

    private int getIdWayType(String namedItem) {
        List<String> wayTypes = ParseHelper.getWayTypes();
        return wayTypes.indexOf(namedItem);
    }
}
