package mapObjects;

import java.util.Set;

public class MapData {
    private final Set<Node> nodes;
    private final Set<Way> ways;
    private final Set<Long> towerNodesId;
    private final Set<Long> blockedNodes;
    private final Set<Long> blockedWays;

    public MapData(Set<Node> nodes, Set<Way> ways, Set<Long> towerNodesId, Set<Long> blockedNodes, Set<Long> blockedWays) {
        this.nodes = nodes;
        this.ways = ways;
        this.towerNodesId = towerNodesId;
        this.blockedNodes = blockedNodes;
        this.blockedWays = blockedWays;
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

    public Set<Long> getBlockedNodes() {
        return blockedNodes;
    }

    public Set<Long> getBlockedWays() {
        return blockedWays;
    }
}
