//package dijkstra;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileHandling {
	static int vertices, edges, index = 0;
	static Graph g;
	private static Scanner scanTwo;
	//private static Scanner scanTwo;
	/*
	 * This procedure will take a file, and read it, extracting the edges and vertices
	 * to create and return a graph
	 */
	public static Graph CreateGraph(Path fileName) throws IOException
	{
		try (Scanner scanOne = new Scanner(fileName)){
			//System.out.println(scanOne.nextLine());
			while (scanOne.hasNextLine()) {
				scanTwo = new Scanner(scanOne.nextLine());
				scanTwo.useDelimiter(" ");
				if(scanTwo.hasNext()){
					if (index==0){	
						vertices=Integer.parseInt(scanTwo.next());
						edges= Integer.parseInt(scanTwo.next());
						g=new Graph(vertices, edges);
						index++;
					}
					else{
						g.DrawEdge(Integer.parseInt(scanTwo.next()),
								Integer.parseInt(scanTwo.next()),
								Integer.parseInt(scanTwo.next()));
						index++;
					}
				}
			}
		}
		return g;
	}
	
	/*
	 * Function to convert the IP's into 32 bit binary number
	 */
	public static List<String> ConvertToBinary(Path fileName) throws IOException{
		String tempAnswer="";
		List<String> answer = new ArrayList<String>();
		try (Scanner scanOne = new Scanner(fileName)){
			//System.out.println(scanOne.nextLine());
			while (scanOne.hasNextLine()) {
				try{
					long a = ipToLong(scanOne.nextLine());
					tempAnswer = Long.toBinaryString(a);
					answer.add(tempAnswer);
					//System.out.print(a+ " "+ tempAnswer);
				}
				catch(Exception e){
					//Do nothing
				}
			}
		}
		return answer;
	}
	
	private static long ipToLong(String ipAddress) {
		 
		String[] ipAddressInArray = ipAddress.split("\\.");
	 
		long result = 0;
		for (int i = 0; i < ipAddressInArray.length; i++) {
	 
			int power = 3 - i;
			int ip = Integer.parseInt(ipAddressInArray[i]);
			result += ip * Math.pow(256, power);
	 
		}
	 
		return result;
	  }

}
