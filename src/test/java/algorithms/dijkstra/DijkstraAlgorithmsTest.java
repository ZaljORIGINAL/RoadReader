package algorithms.dijkstra;

public class DijkstraAlgorithmsTest {

/*    @Test
    public void findingShortestRoute() throws ParserConfigurationException, IOException, SAXException, URISyntaxException {
        URL url = DijkstraAlgorithmsTest.class.getResource("/toParseSmal.osm");
        Path path = Paths.get(url.toURI());
        OsmParser parser = new OsmParser(path);
        Map<Long, Way> waysMap = parser.getWays();
        Graph graph = parser.convertToGraph(waysMap);

        DijkstraAlgorithms algorithms = new DijkstraAlgorithms(graph);
        Node start = new Node(523086937L, 55.7311784, 52.3950185);
        Node finish = new Node(835753789L, 55.7338274, 52.3938429);

        List<Long> expected = new ArrayList<>();
        expected.add(7685098474L);
        expected.add(523634933L);
        expected.add(523086968L);
        expected.add(531867915L);
        expected.add(904254780L);
        expected.add(904254145L);
        expected.add(523634951L);
        expected.add(523634957L);
        expected.add(7685119796L);
        expected.add(7685119784L);
        expected.add(523086870L);
        expected.add(523086880L);
        expected.add(904255068L);
        expected.add(523086937L);
        expected.add(534108442L);
        expected.add(523086878L);
        expected.add(7685119786L);
        expected.add(904254569L);
        expected.add(523086882L);
        expected.add(523086907L);
        expected.add(904254977L);
        expected.add(904254560L);
        expected.add(523086876L);
        expected.add(523086908L);
        expected.add(835753789L);
        expected.add(7685119703L);
        expected.add(534108441L);

        mapObjects.Route route = algorithms.calculatePath(new LengthWeightCalculator(), start, finish);
        System.out.println(route.toString());
    }*/
}