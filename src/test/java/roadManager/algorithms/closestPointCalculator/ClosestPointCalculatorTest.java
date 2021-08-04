package roadManager.algorithms.closestPointCalculator;

import mapObjects.Node;
import mapObjects.OsmParser;
import org.junit.Test;
import org.xml.sax.SAXException;
import roadManager.algorithms.GeographicPoint;
import roadManager.algorithms.dijkstra.DijkstraAlgorithmsTest;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class ClosestPointCalculatorTest {

    @Test
    public void ClosestPointCalculatorTest() throws URISyntaxException, ParserConfigurationException, IOException, SAXException {
        URL url = ClosestPointCalculatorTest.class.getResource("/ClosestPointTest.osm");
        Path path = Paths.get(url.toURI());
        OsmParser parser = new OsmParser(path);
        Map<Long, Node> nodeMap = parser.getNodes(Stream.of(
                516328375L, 1649106424L, 523087424L,
                523087094L, 610756344L, 535212402L,
                535212383L, 535212377L, 523086937L,
                523634955L, 516328426L, 523634909L,
                516328674L)
                .collect(Collectors.toSet()));

        GeographicPoint point = new GeographicPoint(55.7318813, 52.3960559);

        ClosestPointCalculator calculator = new ClosestPointCalculator(nodeMap);
        Map<GeographicPoint, Node> result = calculator
                .calculate(Stream.of(point).collect(Collectors.toList()));

        Node node = result.get(point);

        assertEquals(nodeMap.get(535212383L), node);
    }
}