//package dijkstra;

import java.util.List;

public class IpRouting {
	Graph graph;
	List<String> ipList = null;
	/*
	 * root of the trie is initialized with a null 
	 * value, it can be initialized with the source
	 * node. 
	 */
	DijkstraFibonacci dFib;
	BinaryTrie [] trie;
	String [][] routingTable;
	int source, destination; 
	/*
	 * to store the shortest distance from the source to destination
	 */
	int distanceFromSource; 

	public IpRouting(Graph g, List<String> ipList, int source, int destination) {
		this.graph=g;
		this.ipList=ipList;
		this.source=source;
		this.destination=destination;
		trie = new BinaryTrie[g.VERTICES];
		/*
		 * to create the next hop table for each of the node. 
		 */
		this.routingTable=new String[g.VERTICES-1][2];
		for (int i=0; i<g.VERTICES;i++){
			trie[i]=new BinaryTrie();
		}
		CreateRoutingTable();
	}
	/*
	 * this function will calculate the shortest path from 
	 * each source node to each node, and then create
	 * a binary trie using the shortest paths created. 
	 */
	private void CreateRoutingTable() {
		for (int i=0; i<graph.VERTICES; i++){
			this.dFib = new DijkstraFibonacci(graph,i,0);
			dFib.CalculateShortestDistance();
			/*
			 * to store the shortest path
			 */
			if(i==this.source){
				distanceFromSource=dFib.node[destination].getPriority();
			}
			/*
			 * dFib.shortestPaths has the shortest distance from 
			 * source node i to each other node. 
			 * now we need to insert this record into the 
			 * binary trie. 
			 */
			String nextHop;
			int index = 0;
			for(int j=0; j<graph.VERTICES; j++){
				//System.out.println(j+" "+i);
				//routingTable[index][0]=ipList.get(j);
				if(j!=i)//if source node and the destination nope aren't the same
				{
					routingTable[index][0]=ipList.get(j);
					nextHop = dFib.shortestPaths[j].get(1).toString();
					routingTable[index][1]=nextHop;
					index++;
				}
				/*else
				{
					nextHop = dFib.shortestPaths[j].get(0).toString();
				}
				routingTable[j][1]=nextHop;*/
			}
			trie[i].InsertRoute(routingTable);
		}
	}
	
	/*
	 * to find the shortest path, the next hop 
	 * is searched in source binary trie and then the 
	 * search is moved on to the next hop's binary 
	 * trie. 
	 */
	public void FindPath(){
		System.out.println(this.distanceFromSource);
		String destAddress=ipList.get(this.destination);
		String destString = Integer.toString(destination);
		String nextHop = trie[this.source].SearchNextHop(destAddress);
		while(!nextHop.equals(destString)){
			//search for index. 
			int nextHopIndex = Integer.parseInt(nextHop);
			nextHop=trie[nextHopIndex].SearchNextHop(destAddress);
		}
		
	}

}
