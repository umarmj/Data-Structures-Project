//package dijkstra;

public class TrieNode {
	NodeType Type;
	String Content;
	TrieNode LeftChild;
	TrieNode RightChild;
	TrieNode Parent;
	public TrieNode getParent() {
		return this.Parent;
	}
	public void setParent(TrieNode parent) {
		this.Parent = parent;
	}
	//To initialize a node of the trie
	public TrieNode(NodeType nodeType, String content){
		this.Type= nodeType;
		this.Content = content;
		this.LeftChild=null;
		this.RightChild=null;
	}

}
