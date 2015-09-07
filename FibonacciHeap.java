//package dijkstra;

import java.util.*;

/*
 * This class will create a Fibonacci heap to 
 * be used in DijkstraFibonacci class to 
 * calculate the shortest path. 
 */

public class FibonacciHeap {

	// Pointer to the minimum element in the heap.
	private FibNode Min = null;

	private int Size = 0;

	// Inserts the specified element into the Fibonacci heap with the specified priority. 
	public FibNode enqueue(int value, int priority) {
		checkPriority(priority);
		FibNode result = new FibNode(value, priority);
		Min = mergeLists(Min, result);
		++Size;
		return result;
	}

	//return Fibonacci heap minimum element
	public FibNode min() {
		if (isEmpty())
			throw new NoSuchElementException("Heap is empty.");
		return Min;
	}

	//returns if heap is empty
	public boolean isEmpty() {
		return Min == null;
	}

	//returns heap size
	public int size() {
		return Size;
	}

	// dequeue the min element in the heap
	public FibNode dequeueMin() {
		/* Check for whether we're empty. */
		if (isEmpty())
			throw new NoSuchElementException("Heap is empty.");
		--Size;

		FibNode minElem = Min;

		if (Min.Next == Min) {
			Min = null;
		}
		else {
			Min.Prev.Next = Min.Next;
			Min.Next.Prev = Min.Prev;
			Min = Min.Next;
		}

		if (minElem.Child != null) {
			FibNode curr = minElem.Child;
			do {
				curr.Parent = null;
				curr = curr.Next;
			} while (curr != minElem.Child);
		}

		Min = mergeLists(Min, minElem.Child);

		if (Min == null) return minElem;

		List<FibNode> treeTable = new ArrayList<FibNode>();

		List<FibNode> toVisit = new ArrayList<FibNode>();

		for (FibNode curr = Min; toVisit.isEmpty() || toVisit.get(0) != curr; curr = curr.Next)
			toVisit.add(curr);

		for (FibNode curr: toVisit) {
			while (true) {
				while (curr.Degree >= treeTable.size())
					treeTable.add(null);

				if (treeTable.get(curr.Degree) == null) {
					treeTable.set(curr.Degree, curr);
					break;
				}

				FibNode other = treeTable.get(curr.Degree);
				treeTable.set(curr.Degree, null);

				FibNode min = (other.Priority < curr.Priority)? other : curr;
				FibNode max = (other.Priority < curr.Priority)? curr  : other;

				max.Next.Prev = max.Prev;
				max.Prev.Next = max.Next;

				max.Next = max.Prev = max;
				min.Child = mergeLists(min.Child, max);

				max.Parent = min;

				max.IsMarked = false;

				++min.Degree;

				curr = min;
			}
			if (curr.Priority <= Min.Priority) Min = curr;
		}
		return minElem;
	}


	/*
	 * This method will decrease the key of an element
	 * the method is used to decrease the shortest path 
	 * value
	 */
	public void decreaseKey(FibNode node, int newPriority) {
		checkPriority(newPriority);
		if (newPriority > node.Priority)
			throw new IllegalArgumentException("New priority exceeds old.");

		decreaseKeyUnchecked(node, newPriority);
	}

	//to delete a node
	public void delete(FibNode node) {
		decreaseKeyUnchecked(node, Integer.MIN_VALUE);
		dequeueMin();
	}

	//return the priority of an element
	private void checkPriority(double priority) {
		if (Double.isNaN(priority))
			throw new IllegalArgumentException(priority + " is invalid.");
	}


	/*
	 * this method will merges two fib heaps
	 * in linked lists, the method is used in 
	 * delete min procedure
	 */
	private static  FibNode mergeLists(FibNode one, FibNode two) {
		if (one == null && two == null) {
			return null;
		}
		else if (one != null && two == null) {
			return one;
		}
		else if (one == null && two != null) {
			return two;
		}
		else {
			FibNode oneNext = one.Next; // Cache this since we're about to overwrite it.
			one.Next = two.Next;
			one.Next.Prev = one;
			two.Next = oneNext;
			two.Next.Prev = two;

			return one.Priority < two.Priority? one : two;
		}
	}
	/*
	 * To implement cascading cut
	 */

	private void decreaseKeyUnchecked(FibNode node, int priority) {
		node.Priority = priority;

		if (node.Parent != null && node.Priority <= node.Parent.Priority)
			cutNode(node);

		if (node.Priority <= Min.Priority)
			Min = node;
	}

	private void cutNode(FibNode node) {
		node.IsMarked = false;

		if (node.Parent == null) return;

		if (node.Next != node) { // Has siblings
			node.Next.Prev = node.Prev;
			node.Prev.Next = node.Next;
		}

		if (node.Parent.Child == node) {
			if (node.Next != node) {
				node.Parent.Child = node.Next;
			}
			else {
				node.Parent.Child = null;
			}
		}

		--node.Parent.Degree;

		node.Prev = node.Next = node;
		Min = mergeLists(Min, node);

		if (node.Parent.IsMarked)
			cutNode(node.Parent);
		else
			node.Parent.IsMarked = true;

		node.Parent = null;
	}
}