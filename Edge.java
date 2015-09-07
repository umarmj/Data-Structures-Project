//package dijkstra;

/*
 * this class creates a weighted edge to be inserted
 * into the adjacency list of the Graph. 
 */
public class Edge {
	private int to; 
	private int weight;
	
	public Edge(int to, int weight){
		this.to=to;
		this.weight=weight;
	}
	
	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
