//package dijkstra;
/*
 * This class will create a new Binary Trie. 
 */
public class BinaryTrie {
	TrieNode root; //root node pointer. 
	
	public BinaryTrie(){
		root = new TrieNode(NodeType.BranchNode,"");
	}
	
	/*
	 * a node will always be inserted with some data. 
	 * this might trigger some insertions of new branch nodes
	 * all the cases are handled in this function
	 */
	public void InsertNode(String data){
		TrieNode newNode= new TrieNode(NodeType.ElementNode, data);
		char[] dataArray = data.toCharArray();
		int index=0;
		//initialize the tree with a new node
		if (root==null){
			root=newNode;
			return;
		}
		TrieNode parent = null;
		TrieNode traverse = root;
		while(traverse!=null){
			parent = traverse;
			//case there is a branch node
			if(traverse.Type==NodeType.BranchNode){
				if(dataArray[index]=='0'){
					traverse=traverse.LeftChild;
				}
				//if dataArry[index] is 1
				else{
					traverse=traverse.RightChild;
				}
				index++;
			}
			if(traverse!=null){
				if(traverse.Type == NodeType.ElementNode){
					break;
				}
			}
		}

		if (traverse == null){
			if(dataArray[index-1]=='0'){
				parent.LeftChild=newNode;
			}
			else if(dataArray[index-1]=='1'){
				parent.RightChild=newNode;
			}
			newNode.Parent=parent;
		}
		//case there is an element node
		//in this case one or many new branch nodes 
		//will be inserted.
		else if(traverse.Type== NodeType.ElementNode){
			//in this case, we have a conflicting node
			//we need to add branch nodes to remove the
			//conflict.
			TrieNode conflictingNode= new TrieNode(NodeType.ElementNode, traverse.Content);
			//for traversal of the content of conflicting node
			char [] temp = conflictingNode.Content.toCharArray();
			//we know that we need to insert atleast one branch node.
			traverse = new TrieNode(NodeType.BranchNode,"");
			
			if(dataArray[index-1]=='0'){
				parent.LeftChild=traverse;
			}
			else parent.RightChild=traverse;
			traverse.Parent=parent;
			int i=index;
			for(i=index;i<dataArray.length;i++){
				parent=traverse;
				//case the next elements are the same too
				if(temp[i] == dataArray[i]){
					traverse=new TrieNode(NodeType.BranchNode,"");
					if (dataArray[i]=='0'){
						//to set the parent of new branch node
						parent.LeftChild = traverse;
						traverse.Parent=parent;
						//traverse=traverse.LeftChild;
					}
					//if dataArray[i] is 1
					else{ 
						//to set the parent of new branch node
						parent.RightChild = traverse;
						traverse.Parent=parent;
						//traverse=traverse.RightChild;
					}
				}
				else break;
			}
				//case they are different
			if (dataArray[i]=='0' && temp[i]=='1'){
				parent.LeftChild=newNode;
				parent.RightChild = conflictingNode;
			}
			else{
				parent.LeftChild=conflictingNode;
				parent.RightChild = newNode;
			}
			newNode.Parent=parent;
			conflictingNode.Parent=parent;
			return;
		}				
						
	}
	
	/*
	 * To find the sibling of a node.
	 */
	public TrieNode Sibling(TrieNode node){
		
		if(node.Parent.LeftChild == node){
			return node.Parent.RightChild;
		}
		else{
			return node.Parent.LeftChild;
		}	
	}
	
	/*
	 * To Delete a node with some specific data
	 * this might trigger the deletions of several 
	 * other branch nodes in the cleanup process. 
	 */
	public void delete(String Data){
		TrieNode parent = null;
		TrieNode sibling=null;
		TrieNode traverse = root;
		int index = 0;
		char [] array = Data.toCharArray();
		//System.out.print("root->");
		while(traverse!=null){
			parent = traverse;
			if (traverse.Type == NodeType.BranchNode){
				if(array[index] == '0'){
					//System.out.print(" 0 ->");
					traverse=traverse.LeftChild;
				}
				else{
					//System.out.print(" 1 ->");
					traverse=traverse.RightChild;
				}
				index++;
			}
			if(traverse.Type==NodeType.ElementNode){
				if(Data==traverse.Content){
					sibling = Sibling(traverse);
					break;
				}
				else{
					System.out.print(" Element Node reached, can't Delete ");
					return;
				}
			}
			
		}
		if(traverse==null){
			System.out.print(" No Data. Can't Delete ");
			return;
		}
		//deleting data
		else{
			if (array[index-1]=='0'){
				parent.LeftChild=null;
			}
			else 
				parent.RightChild=null;
		}
		//cleaning up
		if (sibling.Type==NodeType.BranchNode){
			return;//we are good to go. 
		}
		else{
			traverse = parent;
			while(Sibling(sibling) == null){
				parent = traverse.Parent;
				if(parent.LeftChild == traverse){
					parent.LeftChild = sibling;
				}
				else{
					parent.RightChild=sibling;
				}
				sibling.Parent=parent;
				traverse = parent;
			}
		}
	}
	
	/*
	 * Search function to verify the correctness
	 * of the Binary trie
	 */
	public void Search(String Data){
		//TrieNode parent = null;
		TrieNode traverse = root;
		int index = 0;
		char [] array = Data.toCharArray();
		System.out.print("root->");
		while(traverse!=null){
			//parent = traverse;
			if (traverse.Type == NodeType.BranchNode){
				if(array[index] == '0'){
					System.out.print(" 0 ->");
					traverse=traverse.LeftChild;
				}
				else{
					System.out.print(" 1 ->");
					traverse=traverse.RightChild;
				}
				index++;
			}
			else{
				if(Data==traverse.Content){
					System.out.print(" Data ");
					System.out.print("\n");
				}
				else{
					System.out.print(" Element Node reached, No Data exists ");
					System.out.print("\n");
				}
				break;
			}
		}
		if(traverse==null){
			System.out.print(" No Data ");
			System.out.print("\n");
		}
	}
	
	/*
	 * this method inserts a routing table of the next hops
	 * into the binary trie(which is a trie of arrays). 
	 * the routing table contains the destination node and 
	 * the next hop calculated from the Fibonacci Dijkstra.
	 * We are essentially constructing the whole trie 
	 * in a single function here.  
	 * format: routingTable[number of IP addresses][2]
	 */

	public void InsertRoute(String[][] routingTable) {
		for (int i=0; i<routingTable.length; i++){
			TrieNode nextHop = new TrieNode(NodeType.ElementNode,routingTable[i][1]);
			char[] route = routingTable[i][0].toCharArray();// the destination;
			TrieNode parent = null;
			TrieNode traverse = root;
			for(int j=0; j< route.length; j++){
				parent = traverse;
				if(j==route.length-1){
					if(route[j]=='0'){
						parent.LeftChild = nextHop;
						nextHop.Parent=parent;
					}
					else{
						parent.RightChild = nextHop;
						nextHop.Parent=parent;
					}
				}
				else{
					if(route[j]=='0'){
						traverse=traverse.LeftChild;
						if(traverse == null){
							traverse = new TrieNode(NodeType.BranchNode, ""); 
							parent.LeftChild = traverse;
							traverse.Parent = parent;
						}				
					}
					else{
						traverse=traverse.RightChild;
						if(traverse == null){
							traverse = new TrieNode(NodeType.BranchNode, ""); 
							parent.RightChild = traverse;
							traverse.Parent = parent;
						}
					}
				}
			}
		} 
		Cleanup(root);
	}
	
	/*
	 * The cleanup process to move the nodes with 
	 * the same address one level up. Its a recursive
	 * procedure which checks the element nodes
	 * and moves them up by comparing the element 
	 * nodes with its siblings. 
	 */
	private void Cleanup(TrieNode node) {
		if(node==null){
			return;
		}
		else if(node.Type == NodeType.ElementNode){
			if(Sibling(node)==null){
				TrieNode newNode=new TrieNode(NodeType.ElementNode,node.Content);
				TrieNode parent = node.Parent;
				TrieNode grandParent= parent.Parent;
				if(grandParent.LeftChild==parent){
					grandParent.LeftChild=newNode;
					newNode.Parent=grandParent;
				}
				else{
					grandParent.RightChild=newNode;
					newNode.Parent=grandParent;
				}
				Cleanup(newNode);
			}
			else if(!Sibling(node).Content.equals(node.Content)){
				return;
			}
			else{
				TrieNode newNode=new TrieNode(NodeType.ElementNode,node.Content);
				TrieNode parent = node.Parent;
				TrieNode grandParent= parent.Parent;
				if(grandParent.LeftChild==parent){
					grandParent.LeftChild=newNode;
					newNode.Parent=grandParent;
				}
				else{
					grandParent.RightChild=newNode;
					newNode.Parent=grandParent;
				}
				Cleanup(newNode);
			}
		}
		else if(node.Type==NodeType.BranchNode){
			if(node.LeftChild!=null && node.RightChild!=null &&
					node.LeftChild.Type==NodeType.ElementNode && 
					node.RightChild.Type==NodeType.ElementNode){
				Cleanup(node.LeftChild);
			}
			else{
				Cleanup(node.LeftChild);
				Cleanup(node.RightChild);
			}
		}
		
	}
	
	/*
	 * This function works like the search function
	 * instead that it returns the next hop that
	 * needs to be searched for the destination 
	 * node. 
	 */

	public String SearchNextHop(String destAddress) {
		TrieNode traverse = root;
		int index = 0;
		char [] array = destAddress.toCharArray();
		//System.out.print("root->");
		while(traverse!=null){
			//parent = traverse;
			if (traverse.Type == NodeType.BranchNode){
				if(array[index] == '0'){
					System.out.print("0");
					traverse=traverse.LeftChild;
				}
				else{
					System.out.print("1");
					traverse=traverse.RightChild;
				}
				index++;
			}
			else{
				System.out.print(" ");
				return traverse.Content;
			}
		}
		if(traverse==null){
			System.out.print(" No Data ");
			System.out.print("\n");
		}
		return null;
	}
	
}
