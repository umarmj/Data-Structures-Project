//package dijkstra;

/*
 * This class defines a node for the Fibonnaci heap.
 */

public class FibNode {
	int     Degree = 0;       // Number of children
	boolean IsMarked = false; // Whether this node is marked
	FibNode Next;   // Next and previous elements in the list
	FibNode Prev;
	FibNode Parent; // Parent in the tree, if any.
	FibNode Child;  // Child node, if any.
	int Elem;     // Element being stored here
	int Priority; // Its priority


	// Returns the element represented by this heap entry
	public int getValue() {
		return Elem;
	}
	//Sets the element associated with this heap entry.
	public void setValue(int value) {
		Elem = value;
	}

	//Returns the priority of this element.
	public int getPriority() {
		return Priority;
	}

	//new Entry that holds the given element with the indicated 
	public FibNode(int elem, int priority) {
		Next = Prev = this;
		Elem = elem;
		Priority = priority;
	}
}
