package mapObjects;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapDataBuilder {
    private Set<Node> nodes = new HashSet<>();
    private Set<Way> ways =  new HashSet<>();
    private Set<Long> towerNodesId = new HashSet<>();
    private Set<Long> blockedNodes = new HashSet<>();

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setWays(Set<Way> ways) {
        this.ways = ways;
    }

    public Set<Way> getWays() {
        return ways;
    }

    public void setTowerNodesId(Set<Long> towerNodesId) {
        this.towerNodesId = towerNodesId;
    }

    public Set<Long> getTowerNodesId() {
        return towerNodesId;
    }

    public void setBlockedNodesId(Set<Long> blockedNodes) {
        this.blockedNodes = blockedNodes;
    }

    public Set<Long> getBlockedNodes() {
        return blockedNodes;
    }

    private Set<Edge> buildEdges() {
        Set<Edge> edges = new HashSet<>();

        Map<Long, Node> nodeMap = nodes.stream()
                .collect(Collectors.toMap(Node::getId, node -> node));
        for (Way way : ways) {
            edges.addAll(way.getEdges(nodeMap, towerNodesId, blockedNodes));
        }

        return edges;
    }

    public MapData build(){
        return new MapData(nodes, ways, towerNodesId, blockedNodes, buildEdges());
    }
}
