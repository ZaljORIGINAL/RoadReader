package mapObjects;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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

        Way way = waysMap.get(69920363);

        if (way == null)
            fail();

        System.out.println("Id: " + way.getId());
        System.out.println("Node count: " + way.getNodes().size());
        System.out.println("Way type: " + ParseHelper.getWayTypes().get(way.getWayType()));
        System.out.println("Max speed: " + way.getMaxSpeed());

        if (way.getNodes().size() != 7)
            fail();

        List<String> wayTypes = ParseHelper.getWayTypes();
        int primaryId = wayTypes.indexOf(ParseHelper.PRIMARY_TYPE);
        if (way.getWayType() != primaryId)
            fail();

        if (way.getMaxSpeed() != 60)
            fail();
    }
}