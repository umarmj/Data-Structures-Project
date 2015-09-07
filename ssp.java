//package dijkstra;

import java.io.*;
import java.nio.file.*;
public class ssp {

	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			Path fileName = Paths.get(args[0]);
			int sourceNode = Integer.parseInt(args[1]);
			int destinationNode = Integer.parseInt(args[2]);
			Graph g=FileHandling.CreateGraph(fileName);
			DijkstraFibonacci dijkstraFibonacci = new DijkstraFibonacci(g,sourceNode,destinationNode);
			dijkstraFibonacci.CalculateShortestDistance();
			dijkstraFibonacci.DisplayShortestPaths();
		}
		//testing
		else{
			//Path fileName = Paths.get("C:/Users/Umar/workspace/project.datastructures/input_5000_1_part1.txt");
			//Path fileName = Paths.get("C:/Users/Umar/workspace/project.datastructures/input_graphsmall_part2.txt");
			//Path fileName = Paths.get("C:/Users/Umar/workspace/project.datastructures/sample_input_part1.txt");
			Path fileName = Paths.get("C:/Users/Umar/workspace/project.datastructures/input_1000000.txt");
			int sourceNode=0;
			int destinationNode=999999;
			Graph g=FileHandling.CreateGraph(fileName);
			//g.Display();
			
			DijkstraFibonacci dijkstraFibonacci = new DijkstraFibonacci(g,sourceNode,destinationNode);
			dijkstraFibonacci.CalculateShortestDistance();
			dijkstraFibonacci.DisplayShortestPaths();
		}

	}

}
