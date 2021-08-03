package mapObjects;

import org.junit.Test;
import org.xml.sax.SAXException;
import roadManager.algorithms.Graph;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.Assert.*;

public class WayTest {

    @Test
    public void getEdges() throws URISyntaxException, ParserConfigurationException, IOException, SAXException {
        URL url = WayTest.class.getResource("/readNodeTest.osm");
        Path path = Paths.get(url.toURI());
        OsmParser osmParser = new OsmParser(path);
        Map<Long, Way> ways = osmParser.getWays();
        Graph graph = osmParser.convertToGraph(ways);
        Map<Long, Edge> edgeMap = graph.getAllEdges();

        if (edgeMap.size() != 4)
            fail();
    }
}