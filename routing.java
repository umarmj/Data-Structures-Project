//package dijkstra;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class routing {

	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			Path ipGraph= Paths.get(args[0]);
			Path ipFile = Paths.get(args[1]);
			int sourceNode = Integer.parseInt(args[2]);
			int destinationNode = Integer.parseInt(args[3]);
			List<String> ipList = FileHandling.ConvertToBinary(ipFile);
			Graph g = FileHandling.CreateGraph(ipGraph);
			IpRouting ipRouting=new IpRouting(g,ipList,sourceNode,destinationNode);
			ipRouting.FindPath();
		}
		//for testing purposes
		else{
			Path ipFile = Paths.get("C:/Users/Umar/workspace/project.datastructures/input_ipsmall_part2.txt");
			Path ipGraph= Paths.get("C:/Users/Umar/workspace/project.datastructures/input_graphsmall_part2.txt");
			List<String> ipList = FileHandling.ConvertToBinary(ipFile);
			Graph g = FileHandling.CreateGraph(ipGraph);
			IpRouting ipRouting=new IpRouting(g,ipList,0,3);
			ipRouting.FindPath();
		}

	}

}
