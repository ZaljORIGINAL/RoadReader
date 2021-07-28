package mapObjects;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.Assert.*;

public class OsmParserTest {

    @Test
    public void getWaysTest() throws URISyntaxException, ParserConfigurationException, IOException, SAXException {
        URL url = OsmParserTest.class.getResource("/wayDataTest.osm");
        Path path = Paths.get(url.toURI());
        OsmParser parser = new OsmParser(path);
        Map<Integer, Way> waysMap = parser.getWays();
        if (waysMap.size() != 1)
            fail();

        Way way = waysMap.get(42098172);
        if (way == null)
            fail();

        if (way.getNodes().size() != 7)
            fail();

        if (way.getWayDirection() != 0)
            fail();

        System.out.println("Все классно...");
    }
}