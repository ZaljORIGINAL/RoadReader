package algorithms.pointsFinders;

public class ClosestPointCalculatorTest {

/*    @Test
    public void ClosestPointCalculatorTest() throws URISyntaxException, ParserConfigurationException, IOException, SAXException {
        URL url = ClosestPointCalculatorTest.class.getResource("/ClosestPointTest.osm");
        Path path = Paths.get(url.toURI());
        OsmParser parser = new OsmParser(path);
        Set<Node> nodes = parser.getNodes(Stream.of(
                516328375L, 1649106424L, 523087424L,
                523087094L, 610756344L, 535212402L,
                535212383L, 535212377L, 523086937L,
                523634955L, 516328426L, 523634909L,
                516328674L)
                .collect(Collectors.toSet()));

        GeographicPoint point = new GeographicPoint(55.7318813, 52.3960559);

        ClosestPointCalculator calculator = new ClosestPointCalculator(nodes);
        Map<GeographicPoint, Node> result = calculator
                .calculate(Stream.of(point).collect(Collectors.toList()));

        Node node = result.get(point);

        assertEquals(nodes.stream().filter(tmpNode -> tmpNode.getId() == 535212383L).findFirst().orElse(null), node);
    }*/
}