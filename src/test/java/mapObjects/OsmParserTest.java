package mapObjects;

import org.junit.Test;
import org.xml.sax.SAXException;
import roadManager.algorithms.Graph;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.*;

public class OsmParserTest {

/*    @Test
    public void getWaysTest() throws URISyntaxException, ParserConfigurationException, IOException, SAXException {
        URL url = OsmParserTest.class.getResource("/wayDataTest.osm");
        Path path = Paths.get(url.toURI());
        OsmParser parser = new OsmParser(path);
        Map<Long, Way> waysMap = parser.getWays();
        if (waysMap.size() != 1)
            fail();

        Way way = waysMap.get(69920363L);

        if (way == null)
            fail();

        System.out.println("Id: " + way.getId());
        System.out.println("Node count: " + way.getNodes().size());
        System.out.println("Way type: " + OsmParseHelper.getRoadTypes().get(way.getWayType()));
        System.out.println("Max speed: " + way.getSpeed());

        if (way.getNodes().size() != 7)
            fail();

        List<String> wayTypes = OsmParseHelper.getRoadTypes();
        int primaryId = wayTypes.indexOf(OsmParseHelper.PRIMARY_TYPE);
        if (way.getWayType() != primaryId)
            fail();

        if (way.getSpeed() != 60)
            fail();
    }*/

/*    @Test
    public void getNodesTest() throws ParserConfigurationException, IOException, SAXException {
        URL url = OsmParserTest.class.getResource("/readNodeTest.osm");
        Path path = Paths.get(url.getPath());
        OsmParser parser = new OsmParser(path);
        Map<Long, Way> waysMap = parser.getWays();
        Map<Long, Node> waysNodes = parser.getNodes(waysMap.values());

        if (waysNodes.size() != 6)
            fail();

        Long[] expected = new Long[] {
                41L, 11L, 7L, 27L ,31L, 50L
        };

        for (Long expectedId : expected) {
            Node node = waysNodes.get(expectedId);
            if (node == null)
                fail();
        }

        Long[] unexpected = new Long[]{
                14L, 73L
        };

        for (Long unexpectedId : unexpected){
            Node node = waysNodes.get(unexpectedId);
            if (node != null)
                fail();
        }
    }*/
/*

    @Test
    public void convertToGraphTest() throws ParserConfigurationException, IOException, SAXException, URISyntaxException {
        URL url = OsmParserTest.class.getResource("/readNodeTest.osm");
        Path path = Paths.get(url.toURI());
        OsmParser parser = new OsmParser(path);
        Map<Long, Way> waysMap = parser.getWays();
        Graph graph = parser.convertToGraph(waysMap);

        if (graph == null)
            fail();

        if (graph.getAllNodes().size() != 6)
            fail();

        if (graph.getAllEdges().size() != 4)
            fail();
    }
*/

/*    public void hhh(){
        int nodesIdsListLength = nodesElements.getLength();
        int countElementsToOneThread = nodesIdsListLength/4;
        Thread thread1 = new Thread(() -> {
            for (int index = 0; index < countElementsToOneThread; index++){
                LOGGER.info("Обрабатывается " + index + 1 +" из " + nodesElements.getLength());
                Element nodeElement = (Element) nodesElements.item(index);

                NamedNodeMap fieldAttrMap = nodeElement.getAttributes();
                long id;
                double lat;
                double lon;

                //Получаем id
                String idStr = fieldAttrMap.getNamedItem(ParseHelper.ID_ATTRIBUTE)
                        .getNodeValue();
                id = Long.parseLong(idStr);

                if (!nodesIds.contains(id))
                    continue;

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
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            for (int index = countElementsToOneThread; index < countElementsToOneThread * 2; index++){
                LOGGER.info("Обрабатывается " + index + 1 +" из " + nodesElements.getLength());
                Element nodeElement = (Element) nodesElements.item(index);

                NamedNodeMap fieldAttrMap = nodeElement.getAttributes();
                long id;
                double lat;
                double lon;

                //Получаем id
                String idStr = fieldAttrMap.getNamedItem(ParseHelper.ID_ATTRIBUTE)
                        .getNodeValue();
                id = Long.parseLong(idStr);

                if (!nodesIds.contains(id))
                    continue;

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
        });
        thread2.start();

        Thread thread3 = new Thread(() -> {
            for (int index = countElementsToOneThread * 2; index < countElementsToOneThread * 3; index++){
                LOGGER.info("Обрабатывается " + index + 1 +" из " + nodesElements.getLength());
                Element nodeElement = (Element) nodesElements.item(index);

                NamedNodeMap fieldAttrMap = nodeElement.getAttributes();
                long id;
                double lat;
                double lon;

                //Получаем id
                String idStr = fieldAttrMap.getNamedItem(ParseHelper.ID_ATTRIBUTE)
                        .getNodeValue();
                id = Long.parseLong(idStr);

                if (!nodesIds.contains(id))
                    continue;

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
        });
        thread3.start();

        Thread thread4 = new Thread(() -> {
            for (int index = countElementsToOneThread * 3; index < nodesElements.getLength(); index++){
                LOGGER.info("Обрабатывается " + index + 1 +" из " + nodesElements.getLength());
                Element nodeElement = (Element) nodesElements.item(index);

                NamedNodeMap fieldAttrMap = nodeElement.getAttributes();
                long id;
                double lat;
                double lon;

                //Получаем id
                String idStr = fieldAttrMap.getNamedItem(ParseHelper.ID_ATTRIBUTE)
                        .getNodeValue();
                id = Long.parseLong(idStr);

                if (!nodesIds.contains(id))
                    continue;

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
        });
        thread4.start();
    }*/
}