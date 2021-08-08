package mapObjects.parseConfigurations;

public class MaxSpeedConfigurationTest {

/*    @Test
    public void maxSpeedConfigTest() throws URISyntaxException, ParserConfigurationException, IOException, SAXException {
        URL urlToMap = MaxSpeedConfigurationTest.class.getResource("/SpeedConfigTest.osm");
        Path map = Paths.get(urlToMap.toURI());
        OsmParser parser = new OsmParser(map);

        URL urlToMaxSpeedConfig = MaxSpeedConfigurationTest.class.getResource("/SpeedConfig.xml");
        Path maxSpeedConfigFile = Paths.get(urlToMaxSpeedConfig.toURI());
        MaxSpeedConfiguration maxSpeedConfiguration = new
                MaxSpeedConfiguration(maxSpeedConfigFile);

        parser.setMaxSpeedConfiguration(maxSpeedConfiguration);

        Map<Long, Way> waysMap = parser.getWays();

        if (waysMap == null || waysMap.size() == 0)
            fail();

        if (waysMap.get(52640651L).getSpeed() != 20)
            fail();

        if (waysMap.get(41576382L).getSpeed() != 35)
            fail();

        if (waysMap.get(54139452L).getSpeed() != 45)
            fail();

        if (waysMap.get(55483304L).getSpeed() != 55)
            fail();

        if (waysMap.get(58528987L).getSpeed() != 60)
            fail();
    }*/
}