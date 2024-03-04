import java.util.ArrayList;

/**
 * BinomialHeap
 *
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */
public class BinomialHeap
{
	public int size;
	public HeapNode last;
	public HeapNode min;
	public ArrayList<HeapNode> subTreesArray;

	// Constructor
	public BinomialHeap() {
		this.size = 0;
		this.last = null;
		this.min = null;
		this.subTreesArray = new ArrayList<HeapNode>();
	}

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapItem.
	 *
	 */
	public HeapItem insert(int key, String info) 
	{
		HeapItem newHeapItem = new HeapItem(key, info);
		HeapNode newNode = new HeapNode(newHeapItem);

		// If a tree of rank 0 doesn't exist, or the Heap is empty - just insert a single node (tree of rank 0)
		if ((this.size() == 0) || (this.subTreesArray.get(0) == null)) {
			this.subTreesArray.add(0, newNode);

			// Update minimum if needed
			if ((this.min == null) || (this.min.getKey() > key)) {
				this.min = newNode;
			}
		}

		// Else, call a meld chain to connect each two trees of the same rank
		singleMeld(newNode);

		// Update size
		this.size++;

		return newHeapItem;
	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin()
	{
		return; // should be replaced by student code

	}

	/**
	 * 
	 * Return the minimal HeapItem
	 *
	 */
	public HeapItem findMin()
	{
		return null; // should be replaced by student code
	} 

	/**
	 * 
	 * pre: 0 < diff < item.key
	 * 
	 * Decrease the key of item by diff and fix the heap. 
	 * 
	 */
	public void decreaseKey(HeapItem item, int diff) 
	{    
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the item from the heap.
	 *
	 */
	public void delete(HeapItem item) 
	{    
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(BinomialHeap heap2)
	{
		// We are melding between single node heap and a binomial heap
		if (heap2.size() == 1) {
			singleMeld(heap2.min);
		}

		return; // should be replaced by student code   		
	}

	/**
	 * 
	 *
	 */
	public void singleMeld(HeapNode newNode) {

		// If the binomial heap doesn't have a rank 0 tree
		if (this.subTreesArray.get(0) == null) {
			this.insert(newNode.getKey(), newNode.getInfo());
		}

		// Else, we must start meld chain

		// 1. Start index counter and pointers to initial nodes
		// 2. Iterate until we reach a null rank, meaning we have no more trees to meld in chain
		int indexCnt = 0;
		HeapNode prevNode = newNode;
		HeapNode currNode = this.subTreesArray.get(indexCnt);

		while (currNode != null) {

			// Prev node is minimal, thus becomes the new root
			if (prevNode.getKey() < currNode.getKey()) {

				uniteNodes(prevNode, currNode);
			}

			// Curr node is minimal, thus we add the prev one as it's child
			else {
				uniteNodes(currNode, prevNode);

				// Update our iterating node
				prevNode = currNode;
			}

			// Zero prev rank
			this.subTreesArray.add(indexCnt, null);

			// Increment indexCnt
			indexCnt++;

			// Update pointers to keep iterating up the ranks of the heap
			currNode = this.subTreesArray.get(indexCnt);

		} 
	}

	/*
	 * 
	 */
	public void uniteNodes(HeapNode minNode, HeapNode otherNode) {

		// Update pointers of both nodes
		otherNode.setNext(minNode.getChild());
		otherNode.setParent(otherNode);
		minNode.setChild(otherNode);

		// Update rank of the minNode
		minNode.setRank(minNode.getRank()+1);

	}

	/**
	 * 
	 * Return the number of elements in the heap
	 *   
	 */
	public int size()
	{
		return this.size;
	}

	/**
	 * 
	 * The method returns true if and only if the heap
	 * is empty.
	 *   
	 */
	public boolean empty()
	{
		return false; // should be replaced by student code
	}

	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees()
	{
		return 0; // should be replaced by student code
	}

	/**
	 * Class implementing a node in a Binomial Heap.
	 *  
	 */
	public class HeapNode{
		public HeapItem item;
		public HeapNode child;
		public HeapNode next;
		public HeapNode parent;
		public int rank;

		// Constructor
		public HeapNode(HeapItem item){
			this.item = item;
			this.child = null;
			this.next = null;
			this.parent = null;
			this.rank = 0;
			this.item.setNode(this);
		}

		/* All getters and setters for HeapNode class */

		public int getKey() {
			return this.item.getItemKey();
		}

		public String getInfo() {
			return this.item.getItemInfo();
		}

		public HeapNode getChild() {
			return this.child;
		}

		public HeapNode getNext() {
			return this.next;
		}

		public HeapNode getParent() {
			return this.parent;
		}

		public int getRank() {
			return this.rank;
		}

		public void setItem(HeapItem myItem) {
			this.item = myItem;
		}

		public void setChild(HeapNode myChild) {
			this.child = myChild;
		}

		public void setNext(HeapNode myNext) {
			this.next = myNext;
		}

		public void setParent(HeapNode myParent) {
			this.parent = myParent;
		}

		public void setRank(int myRank) {
			this.rank = myRank;
		}
	}

	/**
	 * Class implementing an item in a Binomial Heap.
	 *  
	 */
	public class HeapItem{
		public HeapNode node;
		public int key;
		public String info;

		// Constructor
		public HeapItem(int key,String info) {
			this.key = key;
			this.info = info;
		}

		// getter for key and info of item

		public int getItemKey() {
			return this.key;
		}

		public String getItemInfo() {
			return this.info;
		}

		// setter for a node pointer

		public void setNode(HeapNode myNode) {
			this.node = myNode;
		}
	}

	/*******************************************/
	/* NOT OUR CODE - USED FOR PRINTING HEAPS */
	/*****************************************/

	public class PrintHeap {
		static String arrowUp;
		static String arrowEndOfLayer;
		static String arrowStartOfLayer;

		public static void printHeap(BinomialHeap heap, boolean unicode) {
			if ( unicode ) {
				arrowUp = "\u2191\n";
				arrowEndOfLayer = "\u21b6\n";
				arrowStartOfLayer = "\u21ba\n";
			} else {
				arrowUp = "^\n";
				arrowEndOfLayer = "~\n";
				arrowStartOfLayer = "*\n";
			}
			if (heap.empty()) {
				System.out.println("Heap is empty");
				return;
			}
			BinomialHeap.HeapNode first = heap.last.next;

			System.out.println("Last: " + heap.last.item.key);
			printTree(first, 0, first); // Print the tree rooted at current root
		}

		private static void printTree(BinomialHeap.HeapNode node, int depth, BinomialHeap.HeapNode initialRoot) {
			if (node.next != initialRoot) {
				printTree(node.next, depth, initialRoot); // Print sibling recursively until we reach the initial root
				printAtDepth(depth, arrowUp); // arrow up pointing to next, if next is not circular
			} else if ( node.next == initialRoot ) {
			printAtDepth(depth, arrowEndOfLayer);	// arrow looping back
														// indicating we've reached end
														// of next nodes in layer
			}

			printNode(node, depth);
			
			if ( node.next == initialRoot.next ) {
				printAtDepth(depth, arrowStartOfLayer);
			}

			if (node.child != null) {
				printTree(node.child, depth + 1, node.child); // Print child recursively
			}

		}
		private static void printNode(BinomialHeap.HeapNode node, int depth) {
			String keyString = String.valueOf(node.item.key);
			String rankString = String.valueOf(node.rank);
			printAtDepth(depth, keyString + " [" + rankString + "]\n");
		}
		
		private static void printAtDepth(int depth, String format) {
			for (int i = 0; i < depth; i++) {
				System.out.printf("  ");
			}
			System.out.printf(format);
		}
	}

public static void main(String[] args) {

	// Create Heap
	BinomialHeap myHeap = new BinomialHeap();

	// Insert Nodes
	myHeap.insert(1,"1");
	// myHeap.insert(2,"2");
	// myHeap.insert(3,"3");

	// Print
	PrintHeap.printHeap(myHeap, true);
	
	}

}
