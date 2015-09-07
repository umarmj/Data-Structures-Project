//package dijkstra;

import java.util.*;

//import com.sun.javafx.geom.Edge;

public class Graph {
	int iterations;
	boolean[] visited;
	int VERTICES;
	int edgesCreated;//to keep a count of the edges created
	int numberOfEdges;
	ArrayList<Edge>[] edges;
	int filled[];
	
	/*
	 * This method will create a graph from the input 
	 * file using values extracted in File handling
	 */
	@SuppressWarnings("unchecked")
	public Graph(int vertices, int edges) {
		this.visited = new boolean[vertices];
		this.iterations = 20;
		this.edgesCreated = 0;
		this.VERTICES = vertices;
		this.numberOfEdges = edges;
		this.edges = new ArrayList[VERTICES];
		this.filled = new int[vertices];
		for (int j = 0; j < vertices; j++) {
			//edges.get(j).set(new ArrayList<Edge>());
			this.edges[j]=new ArrayList<Edge>();
			filled[j] = vertices;
			visited[j]=false;
		}

	}

	/*
	 * To Draw an edge of the graph
	 */
	public void DrawEdge(int e1, int e2, int w) {
		Edge edge = new Edge(e2,w);
		Edge edge2 = new Edge(e1,w);
		this.edges[e1].add(edge);
		this.edges[e2].add(edge2);
		this.edgesCreated++;
	}
	
	public void Display(){
		System.out.println(this.VERTICES +" "+this.numberOfEdges);
		StringBuilder temp = new StringBuilder("");

		for (int i = 0; i< VERTICES; i++){
			temp = new StringBuilder(i+ " ");
			for(int j=0; j<edges[i].size(); j++){
				//temp.append(" ");
				Edge t = (Edge) edges[i].get(j);
				temp.append(t.getTo()+",");
				temp.append(t.getWeight()+" ");
			}
			System.out.println(temp);
		}
		System.out.println("Edges Created:"+this.edgesCreated);
	}
	

}
