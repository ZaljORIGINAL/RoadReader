package mapObjects;

import org.junit.Test;
import org.xml.sax.SAXException;

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
        Map<Integer, Way> waysMap = parser.getWays();
        if (waysMap.size() != 1)
            fail();

        Way way = waysMap.get(69920363);

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
    public void getNodesToGraphTest() throws ParserConfigurationException, IOException, SAXException {
        //Дорога №1. Содержит точки: 11, 01, 13, 04.
        List<Long> nodesToWay1 = new ArrayList<>();
        nodesToWay1.add(11L);
        nodesToWay1.add(1L);
        nodesToWay1.add(13L);
        nodesToWay1.add(4L);
        Way way1 = new Way(1, nodesToWay1, 2, 60);
        //Дорога №2. Содержит точки: 21, 02, 23, 01.
        List<Long> nodesToWay2 = new ArrayList<>();
        nodesToWay2.add(21L);
        nodesToWay2.add(2L);
        nodesToWay2.add(23L);
        nodesToWay2.add(1L);
        Way way2 = new Way(2, nodesToWay2, 2, 60);
        //Дорога №3. Содержит точки: 31, 03, 33, 02.
        List<Long> nodesToWay3 = new ArrayList<>();
        nodesToWay3.add(31L);
        nodesToWay3.add(3L);
        nodesToWay3.add(33L);
        nodesToWay3.add(2L);
        Way way3 = new Way(3, nodesToWay3, 2, 60);
        //Дорога №4. Содержит точки: 41, 04, 43, 03.
        List<Long> nodesToWay4 = new ArrayList<>();
        nodesToWay4.add(41L);
        nodesToWay4.add(4L);
        nodesToWay4.add(43L);
        nodesToWay4.add(3L);
        Way way4 = new Way(4, nodesToWay4, 2, 60);


        Map<Integer, Way> waysMap = new HashMap<>();
        waysMap.put(way1.getId(), way1);
        waysMap.put(way2.getId(), way2);
        waysMap.put(way3.getId(), way3);
        waysMap.put(way4.getId(), way4);

        URL url = OsmParserTest.class.getResource("/wayDataTest.osm");
        Path path = Paths.get(url.getPath());
        OsmParser parser = new OsmParser(path);
        Set<Long> ids = parser.getTowerNodes(waysMap);

        if (ids.size() != 8)
            fail();

        //Разыскиваемые точки
        long[] expected = new long[]{11, 1, 21, 2, 31, 3, 41, 4};
        for (long id : expected) {
            if (!ids.contains(id))
                fail();
        }

    }
}