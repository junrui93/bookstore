package junrui.data;

import junrui.model.GraphRelation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphResult {

    private List<Map<String, String>> nodes = new ArrayList<>();
    private List<GraphRelation> links = new ArrayList<>();

    public List<Map<String, String>> getNodes() {
        return nodes;
    }

    public void setNodes(List<Map<String, String>> nodes) {
        this.nodes = nodes;
    }

    public List<GraphRelation> getLinks() {
        return links;
    }

    public void setLinks(List<GraphRelation> links) {
        this.links = links;
    }
}
