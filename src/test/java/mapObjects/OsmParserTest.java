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

    @Test
    public void getWaysTest() throws URISyntaxException, ParserConfigurationException, IOException, SAXException {
        URL url = OsmParserTest.class.getResource("/wayDataTest.osm");
        Path path = Paths.get(url.getPath());
        OsmParser parser = new OsmParser(path);
        Map<Long, Way> waysMap = parser.getWays();
        if (waysMap.size() != 1)
            fail();

        Way way = waysMap.get(69920363L);

        if (way == null)
            fail();

        System.out.println("Id: " + way.getId());
        System.out.println("Node count: " + way.getNodes().size());
        System.out.println("Way type: " + ParseHelper.getWayTypes().get(way.getWayType()));
        System.out.println("Max speed: " + way.getSpeed());

        if (way.getNodes().size() != 7)
            fail();

        List<String> wayTypes = ParseHelper.getWayTypes();
        int primaryId = wayTypes.indexOf(ParseHelper.PRIMARY_TYPE);
        if (way.getWayType() != primaryId)
            fail();

        if (way.getSpeed() != 60)
            fail();
    }

    @Test
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
    }

    @Test
    public void convertToGraphTest() throws ParserConfigurationException, IOException, SAXException {
        URL url = OsmParserTest.class.getResource("/readNodeTest.osm");
        Path path = Paths.get(url.getPath());
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
}