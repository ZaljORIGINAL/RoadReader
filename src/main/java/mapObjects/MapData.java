package mapObjects;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapData {
    private final Set<Node> nodes;
    private final Set<Way> ways;
    private final Set<Long> towerNodesId;
    private final Set<Long> blockedNodes;
    private final Set<Edge> edges;

    public MapData(Set<Node> nodes, Set<Way> ways, Set<Long> towerNodesId, Set<Long> blockedNodes, Set<Edge> edges) {
        this.nodes = nodes;
        this.ways = ways;
        this.towerNodesId = towerNodesId;
        this.blockedNodes = blockedNodes;
        this.edges = edges;
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public Set<Way> getWays() {
        return ways;
    }

    public Set<Long> getTowerNodesId() {
        return towerNodesId;
    }

    public Set<Node> getTowerNodes() {
        Map<Long, Node> nodeMap = nodes.stream()
                .collect(Collectors.toMap(Node::getId, node -> node));

        return getTowerNodesId().stream()
                .map(nodeMap::get)
                .collect(Collectors.toSet());
    }

    public Set<Long> getBlockedNodesId() {
        return blockedNodes;
    }

    public Set<Edge> getEdges() {
        return edges;
    }
}
