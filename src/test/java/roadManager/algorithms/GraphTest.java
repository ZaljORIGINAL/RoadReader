package roadManager.algorithms;

import mapObjects.Edge;
import mapObjects.OsmParser;
import mapObjects.OsmParserTest;
import mapObjects.Way;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class GraphTest {

    @Test
    public void getEdgesByNodeIdTest() throws ParserConfigurationException, IOException, SAXException {
        URL url = GraphTest.class.getResource("/readNodeTest.osm");
        Path path = Paths.get(url.getPath());
        OsmParser parser = new OsmParser(path);
        Map<Long, Way> waysMap = parser.getWays();
        Graph graph = parser.convertToGraph(waysMap);

        //Точка 27, должна содержать 8 граней.
        List<Edge> edges = graph.getEdgesByNodeId(27);

        if (edges.size() != 3)
            fail();
    }

    @Test
    public void containsNodeTest() throws ParserConfigurationException, IOException, SAXException {
        URL url = GraphTest.class.getResource("/readNodeTest.osm");
        Path path = Paths.get(url.getPath());
        OsmParser parser = new OsmParser(path);
        Map<Long, Way> waysMap = parser.getWays();
        Graph graph = parser.convertToGraph(waysMap);

        if (!graph.containsNode(41))
            fail();
    }

    @Test
    public void hasInputEdgeTest() throws ParserConfigurationException, IOException, SAXException {
        URL url = GraphTest.class.getResource("/readNodeTest.osm");
        Path path = Paths.get(url.getPath());
        OsmParser parser = new OsmParser(path);
        Map<Long, Way> waysMap = parser.getWays();
        Graph graph = parser.convertToGraph(waysMap);

        if (!graph.hasInputEdge(7))
            fail();
    }

    @Test
    public void hasOutputEdgeTest() throws ParserConfigurationException, IOException, SAXException {
        URL url = GraphTest.class.getResource("/readNodeTest.osm");
        Path path = Paths.get(url.getPath());
        OsmParser parser = new OsmParser(path);
        Map<Long, Way> waysMap = parser.getWays();
        Graph graph = parser.convertToGraph(waysMap);

        if (!graph.hasOutputEdge(41))
            fail();
    }
}