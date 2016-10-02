package junrui.model;

public class Graph {
	
	private String nodeFrom;
	private String nodeEdge;
	private String nodeTo;
	
	private String title;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNodeFrom() {
		return nodeFrom;
	}	
	public void setNodeFrom(String nodeFrom) {
		this.nodeFrom = nodeFrom;
	}
	public String getNodeEdge() {
		return nodeEdge;
	}	
	public void setNodeEdge(String nodeEdge) {
		this.nodeEdge = nodeEdge;
	}
	public String getNodeTo() {
		return nodeTo;
	}
	public void setNodeTo(String nodeTo) {
		this.nodeTo = nodeTo;
	}
	
	@Override
	public String toString()
	{
		return nodeFrom+"->"+nodeEdge+"->"+nodeTo;
	}
	
}
