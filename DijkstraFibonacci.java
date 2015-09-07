//package dijkstra;

import java.util.*;
public class DijkstraFibonacci {
	//int shortestDistance[];
	boolean finalizedShortestDist[];
	int sourceNode;
	int destinationNode;
	ArrayList<Integer>[] shortestPaths;
	FibonacciHeap f_heap;
	FibNode node[]; //To keep the track of the shortest distances. 
	Graph graph;
	
	/*
	 * this method takes a graph parameter and sets variables 
	 * to calculate the shortest distance
	 */
	@SuppressWarnings({ "unchecked" })
	public DijkstraFibonacci(Graph g, int source, int destination){
		this.shortestPaths=new ArrayList[g.VERTICES];
		this.finalizedShortestDist=new boolean[g.VERTICES];
		this.sourceNode=source;
		this.destinationNode=destination;
		for (int i = 0; i < g.VERTICES; i++) { 
			this.shortestPaths[i]=new ArrayList<Integer>(); 
			finalizedShortestDist[i] = false; 
		} 
		this.f_heap = new FibonacciHeap();
		this.node = new FibNode[g.VERTICES]; 
		this.graph = g;
		//shortestDistance[sourceNode] = 0; 
		finalizedShortestDist[sourceNode] = true;	
		node[sourceNode] = f_heap.enqueue(sourceNode, 0);
			
	}
	/*
	 *This method will calculate the shortest distance 
	 *from a source to all the nodes using fibonacci 
	 *heaps.
	 *The function will start from the source node and 
	 *keep on enqueuing new nodes as it traverses more children. 
	 *It is also going to traverse all nodes and calculate the
	 *updated paths from each node. The final result will
	 *be stored in the node variable.  
	 */
	public void CalculateShortestDistance() {
		shortestPaths[sourceNode].add(sourceNode);
		while(!f_heap.isEmpty())
		{
			FibNode lastNode = f_heap.dequeueMin();
			int lastIndex = lastNode.getValue(); 
			int lastWeight = lastNode.getPriority();
			int newWeight;
			
			//Access the adjacency list of the last node dequeued. 
			ArrayList<Edge> adjList = graph.edges[lastIndex];
			for (int i = 0; i<adjList.size(); i++){
				Edge temp = adjList.get(i);
				newWeight = lastWeight + temp.getWeight();
				if(finalizedShortestDist[temp.getTo()]==true){ 
					if (newWeight <node[temp.getTo()].getPriority()){
						f_heap.decreaseKey(node[temp.getTo()], newWeight);
						shortestPaths[temp.getTo()] = new ArrayList<Integer>(shortestPaths[lastIndex]);
						shortestPaths[temp.getTo()].add(temp.getTo());
					}
				}
				else{
					node[temp.getTo()] = f_heap.enqueue(temp.getTo(), newWeight); 
					finalizedShortestDist[temp.getTo()] = true;
					shortestPaths[temp.getTo()] = new ArrayList<Integer>(shortestPaths[lastIndex]);
					shortestPaths[temp.getTo()].add(temp.getTo());
				}
			}
		}
	}
	
	/*
	 * This function Displays the shortest path to 
	 * the destination node. 
	 */
	public void DisplayShortestPaths() { 
		String distance = "" + node[destinationNode].getPriority(); 
		StringBuilder path = new StringBuilder();
		for (int i= 0; i<shortestPaths[destinationNode].size();i++){
			path.append(shortestPaths[destinationNode].get(i)+" ");	
		}
		System.out.println(distance);
		System.out.println(path);
	}
}
